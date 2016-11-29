import {Injectable} from '@angular/core';
import {Item} from './item.model';
import { Http } from '@angular/http';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class CatalogService {
    private catalog: Item[];
    constructor(private http: Http) {

    }
    getCatalog() {
        return this.http.get('~usuario/usuario123')
            .map(
            res => res.json()

            )
            .catch(function (error) {

                return Observable.throw(<Item[]>[
                    {
                        id: 1,
                        name: 'user 1 catch',
                        description: 'Produto 1111111111111111111111',
                        price: '2.5',
                        image_src: 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg'

                    },
                    {
                        id: 2,
                        name: 'user 2 catch',
                        description: 'Produto com data 12/12/1081',
                        price: '2.1',
                        image_src: 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg'

                    }
                ]);
            });
    }
    setCatalog(catalog: Item[]) {
        this.catalog = catalog;
    }
    get(id: number) {
        var item: Item = null;
        return this.http.get('~usuario/usuario123')
            .map(
                res => res.json()
            )
            .catch(function (error) {

                return Observable.throw(<Item>
                    {
                        id: 1,
                        name: 'user 1 catch',
                        description: 'Produto 1111111111111111111111',
                        price: '2.5',
                        image_src: 'http://img5.cliparto.com/pic/s/204746/5102982-monochrome-round-shopping-bag-icon.jpg'

                    }
                ]);
            });
    }
}