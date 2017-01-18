package org.demoiselle.jee7.example.store.product.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Cacheable
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
		@NamedQuery(name = "Product.findByCategoryId", query = "SELECT p FROM Product p WHERE p.category.id = :categoryId"),
		@NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
		@NamedQuery(name = "Product.findByCost", query = "SELECT p FROM Product p WHERE p.cost = :cost") })
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Long id;

	@JoinColumn(name = "category_id", nullable = false, updatable = true)
	@ManyToOne(optional = false)
	private Category category;

	@Size(max = 45)
	@Column(length = 45)
	private String description;

	// @Max(value=?) @Min(value = ?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@NotNull
	@Column(precision = 12)
	// @Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Erro de validação
	// do campo COST")
	// @Pattern(regexp =
	// "(?=.)^\\$?(([1-9][0-9]{0,2}(\\.[0-9]{3})*)|[0-9]+)?(,[0-9]{1,2})?$",
	// message = "Erro de validação do campo COST")
	private Float cost;

	@NotNull
	@Column(name = "quantity")
	@Digits(fraction = 1, integer = 3)
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product() {
	}

	public Product(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Product)) {
			return false;
		}
		Product other = (Product) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.demoiselle.jee7.entity.Product[ id=" + id + " ]";
	}

}
