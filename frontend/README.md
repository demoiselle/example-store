# exemplo-loja

### Início Rápido

Para clonar o repositório:
```bash
# clone o repositório
$ git clone https://github.com/demoiselle/example-store.git exemplo-loja

# navegue para a pasta do projeto
$ cd exemplo-loja

# instale as dependências com npm
$ npm install

# inicie o servidor
$ npm start
```
vá para [http://localhost:7070](http://localhost:7070) no seu navegador.

# Índice

* [Iniciando](#iniciando)
    * [Dependências](#dependencias)
    * [Instalando](#instalando)
    * [Configurando](#configurando)
    * [Desenvolvendo](#desenvolvendo)
    * [Produção](#producao)
* [Perguntas Frequentes](#faq)
* [TypeScript](#typescript)
* [Licença](#licenca)

# Iniciando

## Dependências

O que você precisa para rodar o app:
* `node` e `npm` (Use o [NVM](https://github.com/creationix/nvm) para gerenciar as versões do Node)
* Este projeto utiliza as versões (`v6.x.x`+) do Node (`3.x.x`+) do NPM

## Instalando

* `clone` o repositório e instale suas dependências (Início Rápido)

## Configurando

Múltiplos endpoints podem ser configurados nos arquivos webpack(.dev/.prod/.test) na pasta /config.
Exemplo:
```
'endpoints' : {
    'endpoint1' : 'http://localhost:8080/app/'
}
```

A chave do endpoint pode ser usada para fazer requisições dessa forma:
```
this.http.get('~endpoint1/usuario/')
```

## Desenvolvendo

Depois de instalar todas as dependências com `npm install`, iniciar o servidor com:

* `npm start`

Isso vai iniciar um servidor local usando `webpack-dev-server` que irá monitorar, construir (em memória) e recarregar automaticamente. A aplicação ficará disponível em: `http://localhost:7070`.

Tudo pronto! Agora os componentes podem ser modificados em tempo real sem ter que se preocupar em ficar recarregando a página.

## Produção

Para construir a aplicação, rodar o comando:

* `npm run build`

O conteúdo gerado na pasta `/dist` é o que dever ser implantado no servidor.

# Perguntas Frequentes

#### É preciso adicionar cada script / tag de link no index.html ?

Não, o Webpack adicionará todos os pacotes de Javascript necessários como tags de script e todos os arquivos CSS como tags de link. A vantagem é que você não precisa modificar o index.html toda vez que você cria sua solução para atualizar os seus arquivos.

#### Como incluir bibliotecas angular 2 externas ?

É simples, basta instalar a biblioteca via `npm istall` e importá-la em seu código quando você precisar dela. Utilizar o parâmetro `--save` para incluir a biblioteca no seu projeto:

* `npm install bibliotecaexterna --save`

#### Como incluir css externo como bootstrap.css ?

Basta instalar a biblioteca e importar o arquivo css em um arquivo que agrupe a importação de bibliotecas externas, geralmente chamado de [vendor.ts] (https://github.com/preboot/angular2-webpack/blob/master/src/vendor.ts).

```sh
npm install bibliotecaexterna --save
```

Então em [vendor.ts] adicionar o seguint:

```ts
import 'bibliotecaexterna/dist/css/bibliotecaexterna.css';
```

# TypeScript

> Para aproveitar ao máximo o TypeScript com autocompletar, use um editor com os plugins corretos do TypeScript.

## Usar um editor compatível com TypeScript:

* [Visual Studio Code](https://code.visualstudio.com/)

# Licença

[MIT](/LICENSE)
