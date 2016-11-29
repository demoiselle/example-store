import {Component} from '@angular/core';
import {CatalogService} from './catalog.service';
import {Item} from "./item.model";
//import {ItemPreviewComponent} from "./item-preview.component";


@Component({
    selector:'catalog',
    templateUrl: './catalog.component.html'
})

export class CatalogComponent {
    public catalog:Item[] = [];
    public search:string = "";
    constructor(private catalogService:CatalogService){
    }
    ngOnInit() {
        this.catalogService.getCatalog().subscribe(
            (catalog)=>{
                this.catalog = catalog;
            },
            error => {
                this.catalog = error;
            }
        );
    }
}
