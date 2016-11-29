import { Component, OnInit, ViewChild } from '@angular/core';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';
import { Router, ActivatedRoute } from '@angular/router';
import {CatalogService} from './catalog.service';
import {Item} from "./item.model";
import {CartService} from "./cart.service";


@Component({
    selector: 'item-details',
    templateUrl: './details.component.html'
})
export class DetailsComponent {
    private details: Item;

    private routeSubscribe: any;
    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private catalogService: CatalogService,
        private cartService: CartService) {

    }
    ngOnInit() {
        this.routeSubscribe = this.route.params.subscribe(params => {
            if (params['id']) {
                let id = +params['id']; // (+) converts string 'id' to a number
                this.loadDetailsById(id);
            }
        });
    }

    ngOnDestroy() {
        this.routeSubscribe.unsubscribe();
    }

    loadDetailsById(id: number): void {
        //this.details = this.catalogService.getById(id);

        this.catalogService.get(id)
            .subscribe(
            (item: Item) => {
                this.details = item;
            },
            error => {
                this.details = error;
            }
            );
    }

    addToCart() {
        this.cartService.addItem(this.details);
        console.log(this.cartService.getTotalPrice())
    }

    back() {
        this.router.navigate(['/shopping']);
    }
}