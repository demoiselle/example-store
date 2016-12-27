import {Component, OnInit} from '@angular/core';
import { Router} from '@angular/router';
import {CartItem} from "../cart/cartitem.model";
import {CartService} from "../cart/cart.service";
import {CheckoutService} from './checkout.service';
import {NotificationService} from '../shared/notification.service';



@Component({
    selector:'checkout',
    providers:[],
    templateUrl: './checkout.component.html'
})


export class CheckoutComponent implements OnInit {
    protected cartItems: CartItem[] = [];

    protected totalPriceBeforeDiscount: number = 0;
    protected discount: number = 0;
    protected totalPrice: number = 0;

    protected coupon: string = "";
    protected validCoupon: string = "";

    constructor(
        private cartService:CartService,
        private checkoutService: CheckoutService,
        private router: Router,
        private notificationService: NotificationService
    ){}

    ngOnInit() {
        // salePreview
        this.cartItems = this.cartService.getCart();
        this.checkoutService.salePreview(this.cartItems, []).subscribe(
            (res) => {
                this.processSalePreview(res);
            }
        );
        
    }
    
    setCoupon(name:string){
        if(!name) {
            return;
        }
        this.coupon = name;
        let cartItems = this.cartService.getCart();
        this.checkoutService.salePreview(cartItems, [name]).subscribe(
            (res) => {
                this.processSalePreview(res);
            },
            (error) => {
                this.notificationService.warning('Não foi possível aplicar o cupom informado!');
            }
        );
    }

    confirm() {
        // call api saleComplete, use this.validCoupon

        let cartItems = this.cartService.getCart();
        let couponList = [];
        if (this.validCoupon) {
            couponList.push(this.validCoupon);
        }
        this.checkoutService.saleComplete(cartItems, couponList).subscribe(
            (res) => {
                this.processSaleComplete(res);
            },
            (error) => {
                this.notificationService.warning('Ocorreu um erro ao finalizar a compra!');
            }
        );
    }

    processSalePreview(res){
        this.totalPriceBeforeDiscount = this.cartService.getTotalPrice();
        this.totalPrice = res.valorTotal;

        let descontos = 0;
        for (let item of res.itens) {
            if(item.descontos) {
                descontos += (item.quantidade * item.valor) - item.valorComDesconto; 
            }
        }
        if (descontos == 0 && res.listaCupons.length > 0) {
            this.validCoupon = null;
        } else {
            this.validCoupon = res.listaCupons[0];
        }
        this.discount = descontos;
    }

    processSaleComplete(res){
        console.log('PROCESS SALE COMPLETE');
        console.log(res);

        this.cartService.clearCart();
        this.cartItems = null;

        this.notificationService.success('Compra realizada com sucesso! Valor total da compra: ' + res.valorTotal);
    }
    
    
}