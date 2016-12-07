import {Injectable} from '@angular/core';
import {Item} from './item.model';
import { Http } from '@angular/http';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class CatalogService {

    apiUrl = process.env.CONF.endpoints.product;

    private catalog: Item[];
    constructor(private http: Http) {

    }

    list(rangeStart: number, rangeEnd: number) {
        return this.http.get(this.apiUrl + 'product')
            .map(
                res => {
                    let content = res.json().content;
                    for (let item of content) {
                        item.nome = item.descricao; // copia descricao para o nome, até o backend ter a propriedade
                        item.image_src = 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg';
                    }

                    return content;
                }

            )
            .catch(function (error) {

                let p: Item;
                let prods =<Item[]>[];
                for(let i = rangeStart; i<= rangeEnd; i++){
                    p = new Item();
                    p.id = i;
                    p.nome = 'produto ' + i;
                    p.descricao = 'description prod ' + i;
                    p.valor = 3.2 + i;
                    p.image_src = 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg';
                    prods.push(p);
                }
                return Observable.throw(prods);
            });
    }

    getCatalog() {
        return this.http.get('~usuario/usuario123')
            .map(
            res => res.json()

            )
            .catch(function (error) {

                let p: Item;
                let prods =<Item[]>[];
                for(let i = 1; i< 20; i++){
                    p = new Item();
                    p.id = i;
                    p.nome = 'produto ' + i;
                    p.descricao = 'description prod ' + i;
                    p.valor = 3.2 + i;
                    p.image_src = 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg';
                    prods.push(p);
                }
                return Observable.throw(prods);
            });
    }
    setCatalog(catalog: Item[]) {
        this.catalog = catalog;
    }
    get(id: number) {
        var item: Item = null;
        return this.http.get(this.apiUrl + 'product/' + id)
            .map(
                res => res.json()
            )
            .catch(function (error) {

                return Observable.throw(<Item>
                    {
                        id: 1,
                        nome: 'user 1 catch',
                        descricao: 'Produto 1111111111111111111111',
                        valor: 2.5,
                        image_src: 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg'

                    }
                ]);
            });
    }
}