import {Component} from '@angular/core';
import {CartItem} from "./cartitem.model";
import {CartService} from "./cart.service";
import { Router} from '@angular/router';
import {TenantService} from '../tenant/tenant.service';

@Component({
    selector:'cart',
    providers:[],
    templateUrl: './cart.component.html'
})


export class CartComponent {
    private cartItems: CartItem[] = [];
    private totalPrice: number = 0;
    private paymentOutput: string = "";

    constructor(
        private cartService:CartService,
        private router: Router,
	    private tenantService: TenantService){
            this.cartItems = cartService.getCart();
            this.refreshTotalPrice();
	        tenantService.tenantChanged.subscribe(
                (tenant) => {
                    this.tenantChanged();
                }
            );
    }

    tenantChanged() {
        this.cartItems = [];
        this.totalPrice = 0;
    }
    

    onDelete(item: CartItem) {
        this.cartItems = this.cartService.getCart();
        this.refreshTotalPrice();
    }

    onAmountChange(item: CartItem) {
        this.refreshTotalPrice();
    }

    continueShopping() {
        this.router.navigate(['/shopping']);
    }
    
    private refreshTotalPrice(){
        this.totalPrice = this.cartService.getTotalPrice();
    }

    checkout() {
        this.router.navigate(['/checkout']);
    }
    
}