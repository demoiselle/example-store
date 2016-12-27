import {Injectable} from '@angular/core';
import {Item} from './item.model';
import { Http } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { TenantService } from '../tenant/tenant.service';

@Injectable()
export class CatalogService {

    apiUrl = '~product/';

    private catalog: Item[];
    constructor(private http: Http, protected tenantService: TenantService) {

    }

    list(rangeStart: number, rangeEnd: number) {
        let tenant = this.tenantService.getSelectedTenant().name;
        return this.http.get(this.apiUrl + 'products')
            .map(
                res => {
                    let content = res.json();
                    for (let item of content) {
                        item.name = item.description; // copia descricao para o nome, até o backend ter a propriedade
                        item.image_src = 'http://lorempixel.com/100/100/technics/?'+ Math.random();
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
                    p.name = 'produto ' + i + ' (' + tenant + ')';
                    p.description = 'description prod ' + i;
                    p.cost = 3.2 + i;
                    p.image_src = 'http://lorempixel.com/100/100/technics/?'+ Math.random();
                    prods.push(p);
                }
                return Observable.throw(prods);
            });
    }
    
    get(id: number) {
        var item: Item = null;
        return this.http.get(this.apiUrl + 'products/' + id)
            .map(
                res => {
                    let item = res.json();
                    item.name = item.description; // copia descricao para o nome, até o backend ter a propriedade
                    item.image_src = 'http://lorempixel.com/100/100/technics/?'+ Math.random();
                    return item;
                }
            )
            .catch(function (error) {

                return Observable.throw(<Item>
                    {
                        id: 1,
                        name: 'user 1 catch',
                        description: 'Produto 1111111111111111111111',
                        cost: 2.5,
                        image_src: 'http://lorempixel.com/100/100/technics/?'+ Math.random()

                    }
                );
            });
    }
}