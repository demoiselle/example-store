import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

import {CatalogService} from './catalog.service';
import {Item} from "./item.model";
import {CartService} from "./cart.service";
import {NotificationService} from '../shared/notification.service';

@Component({
    selector: 'item-preview',
    styles:[`
        .row img{
            width:100px;
            height:100px;
        }
        .row b, .item-viewdetails{
            cursor:pointer;
        }
    `],
    templateUrl: './item-preview.component.html'
})

export class ItemPreviewComponent {
    @Input() item:Item;
    constructor(
        private router: Router, 
        private cartService:CartService,
        private notificationService: NotificationService
    ){}

    viewDetails(){
        this.router.navigate( ['/detail', this.item.id]);
    }
    addToCart(){
        this.cartService.addItem(this.item);
        console.log(this.cartService.getTotalPrice());
        //this.notificationService.success('Item adicionado com sucesso ao carrinho!');
        this.router.navigate(['/cart']);
    }
}