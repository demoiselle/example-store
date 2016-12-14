# exemplo-loja

>Atenção: Esteja certo de estar usando a última versão do Node.js e do NPM

### Início Rápido

> ?? Clone/Download the repo then edit `app.ts` inside [`/src/app/app.component.ts`](/src/app/app.component.ts)

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

* [Getting Started](#getting-started)
    * [Dependencies](#dependencies)
    * [Installing](#installing)
    * [Configuring](#configuring)
    * [Developing](#developing)
    * [Testing](#testing)
    * [Production](#production)
    * [Documentation](#documentation)
* [Frequently asked questions](#faq)
* [TypeScript](#typescript)
* [License](#license)

# Iniciando

## Dependências
O que você precisa para rodar o app:
* `node` e `npm` (Use o [NVM](https://github.com/creationix/nvm) para gerenciar as versões do Node)
* Certifique-se se estar usando as versões (`v6.x.x`+) do Node (`3.x.x`+) do NPM

## Instalando

* `clone` o repositório e instale suas dependências (Início Rápido)

## Configurando

Você pode configurar múltiplos endpoints nos arquivos webpack(.dev/.prod/.test) na pasta /config.
Exemplo:
```
'endpoints' : {
    'endpoint1' : 'http://localhost:8080/app/'
}
```

Então você pode usar a chave do endpoint para fazer requisições dessa forma:
```
this.http.get('~endpoint1/usuario/')
```

## Desenvolvendo

Depois de instalar todas as dependências com `npm install` você deve iniciar o servidor com:

* `npm start`

Isso vai iniciar um servidor local usando `webpack-dev-server` que irá monitorar, construir (em memória) e recarregar para você. A aplicação ficará disponível em: `http://localhost:7070`.

Tudo pronto! Agora você pode modificar componentes em tempo real sem ter que se preocupar em ficar recarregando a página.

## Produção

To build your application, run:

* `npm run build`

You can now go to `/dist` and deploy that to your server!

## Documentation

You can generate api docs (using [TypeDoc](http://typedoc.io/)) for your code with the following:

* `npm run docs`

# FAQ

#### Do I need to add script / link tags into index.html ?

No, Webpack will add all the needed Javascript bundles as script tags and all the CSS files as link tags. The advantage is that you don't need to modify the index.html every time you build your solution to update the hashes.

#### How to include external angular 2 libraries ?

It's simple, just install the lib via npm and import it in your code when you need it. Don't forget that you need to configure some external libs in the [bootstrap](https://github.com/preboot/angular2-webpack/blob/master/src/main.ts) of your application.

#### How to include external css files such as bootstrap.css ?

Just install the lib and import the css files in [vendor.ts](https://github.com/preboot/angular2-webpack/blob/master/src/vendor.ts). For example this is how to do it with bootstrap:

```sh
npm install bootstrap@next --save
```

And in [vendor.ts](https://github.com/preboot/angular2-webpack/blob/master/src/vendor.ts) add the following:

```ts
import 'bootstrap/dist/css/bootstrap.css';
```

# TypeScript

> To take full advantage of TypeScript with autocomplete you would have to use an editor with the correct TypeScript plugins.

## Use a TypeScript-aware editor

We have good experience using these editors:

* [Visual Studio Code](https://code.visualstudio.com/)
* [Webstorm 11+](https://www.jetbrains.com/webstorm/download/)
* [Atom](https://atom.io/) with [TypeScript plugin](https://atom.io/packages/atom-typescript)
* [Sublime Text](http://www.sublimetext.com/3) with [Typescript-Sublime-Plugin](https://github.com/Microsoft/Typescript-Sublime-plugin#installation)

# License

[MIT](/LICENSE)
