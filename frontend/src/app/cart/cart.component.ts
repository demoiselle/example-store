import {Component} from '@angular/core';
import {CartItem} from "./cartitem.model";
import {CartService} from "./cart.service";



@Component({
    selector:'cart',
    providers:[],
    templateUrl: './cart.component.html'
})


export class CartComponent {
    private cartItems: CartItem[] = [];
    private totalPrice: number = 0;
    private paymentOutput: string = "";

    constructor(private cartService:CartService){
        this.cartItems = cartService.getCart();
        this.refreshTotalPrice();
    }
    
    setDiscount(name:string){
        this.cartService.applyDiscount(name);
    }

    onDelete(item: CartItem) {
        this.cartItems = this.cartService.getCart();
        this.refreshTotalPrice();
    }

    onAmountChange(item: CartItem) {
        this.refreshTotalPrice();
    }
    
    private refreshTotalPrice(){
        this.totalPrice = this.cartService.getTotalPrice();
    }
    
}