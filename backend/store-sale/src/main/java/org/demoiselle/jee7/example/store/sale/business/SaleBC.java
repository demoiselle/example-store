package org.demoiselle.jee7.example.store.sale.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.demoiselle.jee.core.api.security.SecurityContext;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.persistence.crud.AbstractBusiness;
import org.demoiselle.jee.script.DynamicManager;
import org.demoiselle.jee7.example.store.sale.dao.ItensDAO;
import org.demoiselle.jee7.example.store.sale.dao.RulesDAO;
import org.demoiselle.jee7.example.store.sale.dao.SaleDAO;
import org.demoiselle.jee7.example.store.sale.entity.Cart;
import org.demoiselle.jee7.example.store.sale.entity.ItemCart;
import org.demoiselle.jee7.example.store.sale.entity.Itens;
import org.demoiselle.jee7.example.store.sale.entity.Product;
import org.demoiselle.jee7.example.store.sale.entity.Rules;
import org.demoiselle.jee7.example.store.sale.entity.Sale;
import org.demoiselle.jee7.example.store.sale.security.Credentials;
import org.demoiselle.tenant.hibernate.business.TenantManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SaleBC extends AbstractBusiness<Sale, Long> {

	@Inject
	private DynamicManager dm;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private Token token;

	@Inject
	private RulesDAO rulesDAO;

	@Inject
	private SaleDAO saleDAO;

	private static final Logger logger = Logger.getLogger(SaleBC.class.getName());

	@Inject
	private ItensDAO itensDAO;

	@Inject
	private TenantManager tenantManager;

	/**
	 * Sale preview.
	 * 
	 */
	public Cart salePreview(Cart cart) throws ScriptException {
		return processCartCupom(preProcessingCart(cart));
	}

	public List<Itens> listSaleItens(Long id) {
		return itensDAO.getAllItens(id);
	}

	/**
	 * Complete the sale.
	 * 
	 */
	public Cart saleComplete(Cart cart) throws ScriptException {
		Cart temp_cart = processCartCupom(preProcessingCart(cart));

		if (temp_cart != null) {
			Sale newSale = posProcessingCarrinho(temp_cart);
			newSale.setDatavenda(new Date());

			if (securityContext.isLoggedIn()) {
				newSale.setUsuarioId(securityContext.getUser().getName());
			}
			newSale = this.dao.persist(newSale);

			for (ItemCart item : temp_cart.getItens()) {
				Itens newitem = new Itens();
				newitem.setVenda(newSale);
				newitem.setValor(item.getValorComDesconto().floatValue());
				newitem.setProduto_id(item.getCodigoProduto());
				newitem.setQuantidade(item.getQuantidade());

				itensDAO.persist(newitem);
			}

			temp_cart.setIdSale(newSale.getId());
			posProcessingSale(newSale);
		}

		return temp_cart;
	}

	/**
	 * Validate the cupom
	 * 
	 */
	public Rules validateCupom(String cupom) {

		Rules rule = rulesDAO.findByName(cupom);
		if (rule != null) {
			Date data = new Date();

			if (data.before(rule.getStartDate()) || data.after(rule.getStopDate())) {
				logger.warning("Cupom \"" + cupom + "\" expired.");
			} else {
				logger.info("Cupom \"" + cupom + "\" validated.");
				return rule;
			}
		} else {
			logger.warning("Cupom \"" + cupom + "\" not valid!");
		}

		return null;
	}

	/**
	 * Cart pre-sale-confirm processing.
	 * 
	 */
	public Cart preProcessingCart(Cart cart) throws ScriptException {
		for (ItemCart item : cart.getItens()) {

			Product p = getProduct(item.getCodigoProduto());
			item.setNomeProduto(p.getDescription());
			item.setValor(BigDecimal.valueOf(p.getCost()));
		}
		return cart;
	}

	/**
	 * Cart after-sale-confirm processing.
	 * 
	 */
	public void posProcessingSale(Sale sale) {

		for (Itens item : sale.getListaItens()) {

			Product p = getProduct(item.getProduto_id());

			Integer total = p.getQuantity();
			if (total >= item.getQuantidade()) {
				p.setQuantity(total - item.getQuantidade());

				String key = null;
				if (!securityContext.isLoggedIn()) {
					key = doLogin("string", "string");
				} else
					key = token.getKey();

				putProduct(p, key);
			}
		}
	}

	/**
	 * Cart after-sale-confirm processing.
	 * 
	 */
	public Sale posProcessingCarrinho(Cart cart) throws ScriptException {
		Sale sale = new Sale();
		List<Itens> listaItens = new ArrayList<Itens>();

		for (ItemCart item : cart.getItens()) {

			Product p = getProduct(item.getCodigoProduto());

			Integer total = p.getQuantity();
			if (total >= item.getQuantidade()) {
				p.setQuantity(total - item.getQuantidade());

				String key = null;
				if (!securityContext.isLoggedIn()) {
					key = doLogin("admin-sale@sepro.gov.br", "serpro");
				} else
					key = token.getKey();

				putProduct(p, key);
			}

		}

		sale.setListaItens(listaItens);

		return sale;
	}

	/**
	 * Return the token to perform a service access in other server.
	 * 
	 */
	public String doLogin(String username, String password) {
		Gson gson = new Gson();

		Credentials login = new Credentials();
		login.setUsername(username);
		login.setPassword(password);

		String baseuri = "http://localhost:8080/users/api/v1";
		String tennantName = "/" + tenantManager.getTenantName();
		String service = "/auth/login";

		Client client = Client.create();
		WebResource webResource = client.resource(baseuri + tennantName + service);
		ClientResponse response = (ClientResponse) webResource.accept("application/json").type("application/json")
				.post(ClientResponse.class, gson.toJson(login));

		if (response.getStatus() != 200) {
			throw new RuntimeException("Cannot access the service " + baseuri + tennantName + service + " user:"
					+ username + "  HTTP error code : " + response.getStatus());
		}

		String resposta = response.getEntity(String.class);

		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(resposta).getAsJsonObject();

		return json.get("token").getAsString();

	}

	/**
	 * Get the Product List.
	 * 
	 */
	public Product getProduct(Long id) {
		Client client = Client.create();

		String baseuri = "http://localhost:8080/products/api/v1";
		String tennantName = "/" + tenantManager.getTenantName();
		String service = "/products/" + id;

		WebResource webResource = client.resource(baseuri + tennantName + service);
		ClientResponse response = (ClientResponse) webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Cannot access the service " + baseuri + tennantName + service
					+ ". HTTP error code : " + response.getStatus());
		}

		Product produto = response.getEntity(Product.class);

		return produto;
	}

	/**
	 * Update the product in product service.
	 * 
	 */
	public void putProduct(Product p, String token) {
		try {
			Client client = Client.create();
			Gson gson = new Gson();
			String Authorization = "token " + token;

			String baseuri = "http://localhost:8080/products/api/v1";
			String tennantName = "/" + tenantManager.getTenantName();
			String service = "/products/";

			WebResource webResource = client.resource(baseuri + tennantName + service);
			System.out.println("Call " + baseuri);
			System.out.println("Authorization: " + Authorization);
			System.out.println("PUT :" + gson.toJson(p));

			ClientResponse response = (ClientResponse) webResource.accept("application/json")
					.header("Authorization", Authorization).type("application/json")
					.put(ClientResponse.class, gson.toJson(p));

			if (response.getStatus() != 200) {
				throw new RuntimeException("Cannot access the service " + baseuri + tennantName + service
						+ ". HTTP error code : " + response.getStatus());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Process the cart with/without coupons.
	 * 
	 */
	public Cart processCartCupom(Cart cart) throws ScriptException {
		String engineName = "groovy";

		for (String cupom : cart.getListaCupons()) {
			Rules rule = validateCupom(cupom);

			if (rule != null) {
				dm.loadScript(engineName, cupom, rule.getScript());

				for (ItemCart item : cart.getItens()) {
					SimpleBindings context = new SimpleBindings();
					context.put(item.getClass().getSimpleName(), item);
					context.put(cart.getClass().getSimpleName(), cart);
					dm.eval(engineName, cupom, context); // run the script of
															// rule
				}
				// Remove from cache for tests....
				dm.removeScript(engineName, cupom);
			}

		}
		return cart;
	}

	public List<Sale> listUserSales(String id) {
		return (List<Sale>) saleDAO.listUserSales(id);
	}

}