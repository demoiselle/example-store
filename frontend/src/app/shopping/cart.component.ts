import {Component} from '@angular/core';
import {Item, CartItem} from "./item.model";
import {CartService} from "./cart.service";



@Component({
    selector:'cart',
    providers:[],
    templateUrl: './cart.component.html'
})


export class CartComponent {
    private cartItems: CartItem[] = [];
    private paymentOutput: string = "";
    constructor(private cartService:CartService){
        this.cartItems = cartService.getCart();
    }
    
    setDiscount(name:string){
        this.cartService.applyDiscount(name);
    }
    
}