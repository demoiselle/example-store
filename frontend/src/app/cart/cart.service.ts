import {Injectable} from '@angular/core';
import {Item} from '../shopping/item.model';
import {CartItem} from './cartitem.model';

@Injectable()
export class CartService {

    //private discount:IDiscount;
    addItem(item:Item){
        
        let cart = this._read();

        let itemFound = false;
        for (let i of cart) {
            if (i.id == item.id){
                i.amount++;
                itemFound = true;
                break;
            }
        }
        if(!itemFound) {
            cart.push(new CartItem(item));
        }

        this._write(cart);

    }
    updateItem(item: CartItem) {
        let cart = this._read();

        for (let i of cart) {
            if (i.id == item.id){
                let index = cart.indexOf(i);
                cart[index] = item;
                break;
            }
        }

        this._write(cart);

    }
    deleteItem(item:CartItem){
        let cart = this._read();
        cart = cart.filter(cartItem=>cartItem.id!==item.id);
        this._write(cart);
    }
    clearCart(){
        this._clear();
    }
    applyDiscount(code:string){
        //this.discount = discounts.filter(discount=>discount.code==code)[0];
    }
    getCart():CartItem[]{
        return this._read();
    }
    getTotalPrice(){
        let cart = this._read();
        let totalPrice = cart.reduce((sum, cartItem)=>{
            return sum += (cartItem.valor * cartItem.amount), sum;
        },0);
        // if(this.discount){
        //     totalPrice -= totalPrice=this.discount.amount;
        // }
        return totalPrice;
    }

    private _read() {
        if (localStorage && localStorage.getItem('cart')) {
            return JSON.parse(localStorage.getItem('cart'));
        }
        return [];
    }

    private _write(cart){
        if (localStorage) {
            localStorage.setItem('cart', JSON.stringify(cart));
        }
    }
    private _clear(){
        if (localStorage && localStorage.getItem('cart')) {
            localStorage.removeItem('cart');
        }
    }
}