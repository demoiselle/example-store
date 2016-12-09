import { Component } from '@angular/core';
import { CartItem } from "./cartitem.model";
import { CartService } from "./cart.service";
import { Router } from '@angular/router';
import { TenantService } from '../tenant/tenant.service';
import { LoginService } from '../login/login.service';

@Component({
    selector:'cart',
    providers:[],
    templateUrl: './cart.component.html'
})


export class CartComponent {
    protected cartItems: CartItem[] = [];
    protected totalPrice: number = 0;
    protected paymentOutput: string = "";

    constructor(
        protected cartService:CartService,
        protected router: Router,
	    protected tenantService: TenantService,
        protected loginService: LoginService){
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
    
    protected refreshTotalPrice(){
        this.totalPrice = this.cartService.getTotalPrice();
    }

    checkout() {
        //this.router.navigate(['/checkout']);
        this.loginService.loginIfNot(['/checkout']);
    }
    
}