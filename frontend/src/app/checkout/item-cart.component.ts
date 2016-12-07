import {Component, Input, Output, EventEmitter} from '@angular/core';
import {Router} from '@angular/router';

import {CartItem} from "../cart/cartitem.model";
import {CartService} from "../cart/cart.service";
import {NotificationService} from '../shared/notification.service';

@Component({
    selector: 'item-cart',
    styles:[`
        .row img{
            width:100px;
            height:100px;
        }

    `],
    templateUrl: './item-cart.component.html'
})

export class ItemCartComponent {
    @Input() item:CartItem;

    constructor(
        private router: Router, 
        private cartService:CartService,
        private notificationService: NotificationService
    ){}

    viewDetails(){
        this.router.navigate( ['/detail', this.item.id]);
    }
   
}