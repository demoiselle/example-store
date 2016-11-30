import {Injectable} from '@angular/core';
import {Item} from '../shopping/item.model';
import {CartItem} from './cartitem.model';
//import {DefaultCheckout, IDiscount} from "./checkout.service";
//import {discounts} from "../Mock/discounts.mock.json";

@Injectable()
export class CartService {

    private cart:CartItem[]=[];

    //private discount:IDiscount;
    addItem(item:Item){
        //this.cart.push(item);

        let itemFound = false;
        for (let i of this.cart) {
            if (i.id == item.id){
                i.amount++;
                itemFound = true;
                break;
            }
        }
        if(!itemFound) {
            this.cart.push(new CartItem(item));
        }

    }
    deleteItem(item:Item){
        this.cart = this.cart.filter(cartItem=>cartItem.id!==item.id);
    }
    clearCart(){
        this.cart = [];
    }
    applyDiscount(code:string){
        //this.discount = discounts.filter(discount=>discount.code==code)[0];
    }
    getCart():CartItem[]{
        return this.cart;
    }
    getTotalPrice(){
        let totalPrice = this.cart.reduce((sum, cartItem)=>{
            return sum+=cartItem.price, sum;
        },0);
        // if(this.discount){
        //     totalPrice -= totalPrice=this.discount.amount;
        // }
        return totalPrice;
    }
}