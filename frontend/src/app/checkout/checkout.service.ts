import {Injectable} from '@angular/core';
import {Item} from '../shopping/item.model';
import {CartItem} from '../cart/cartitem.model';
import {Http} from '@angular/http';

@Injectable()
export class CheckoutService {

    constructor(private http: Http){

    }

    salePreview(cartItems: CartItem[], cupons: string[]){
        let data = this.generateSaleData(cartItems, cupons);
        return this.http.post('~sale/sales/salePreview', data).map(
            (res) => res.json()
        );
    }

    saleComplete(cartItems: CartItem[], cupons: string[]){
        let data = this.generateSaleData(cartItems, cupons);
        return this.http.post('~sale/sales/saleComplete', data).map(
            (res) => res.json()
        );
    }

    protected generateSaleData(cartItems: CartItem[], cupons: string[]){
        let data = {
            'itens' : [],
            'listaCupons': cupons
        };

        for (let item of cartItems){
             let itemObj = {
                 'codigoProduto': item.id,
                 'quantidade': item.quantity
             };
             data.itens.push(itemObj);
        }

        return data;
    }
    
}