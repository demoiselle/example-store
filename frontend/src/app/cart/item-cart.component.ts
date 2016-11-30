import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

import {CartItem} from "./cartitem.model";
import {CartService} from "./cart.service";
import {NotificationService} from '../shared/notification.service';

@Component({
    selector: 'item-cart',
    styles:[`
        .row img{
            width:100px;
            height:100px;
        }
        .row b, .item-viewdetails{
            cursor:pointer;
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