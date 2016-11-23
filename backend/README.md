0# Aplicação de Exemplo para Utilização do Framework Demoiselle 3

## Projeto User

* Mantém usuários
* Funcionalidades de autenticação

## Projeto Product

* Mantém produtos

## Projeto Sale

* Mantém vendas
* Mantém carrinho

## Questões Importantes

* Todos os projetos são configurados para Multi Tenancy, ou seja, os serviços deverão ser acessados concatenando o nome do tenant.
* Por padrão do módulo de multitenacy do Demoiselle deverão ser utilizados os serviços  `/api/tenant` para manipulação dos tenants.
