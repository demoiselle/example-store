import {Component} from '@angular/core';
import {CatalogService} from './catalog.service';
import {Item} from "./item.model";
//import {ItemPreviewComponent} from "./item-preview.component";
import { TenantService } from '../tenant/tenant.service';


@Component({
    selector:'catalog',
    styles: [
        `.search-results {
            height: 60rem;
            min-height: 100%;
            max-height: 100%;
            overflow: scroll;
            

        }
        .search-results::-webkit-scrollbar{ 
            display: none; 
        }

        .row {
            overflow: hidden;
        }
        `
    ],
    templateUrl: './catalog.component.html'
})

//height: 60rem;

export class CatalogComponent {
    public catalog:Item[] = [];
    public search:string = "";

    private lastItemIndex = 30;
    private itemsPerSearch = 10;

    constructor(private catalogService:CatalogService, protected tenantService: TenantService) {
        tenantService.tenantChanged.subscribe(
            (tenant) => {
                this.initCatalog();
            }
        );
    }

    initCatalog() {
        this.catalogService.list(1, this.lastItemIndex).subscribe(
            (catalog)=>{
                this.catalog = catalog;
            },
            error => {
                this.catalog = error;
            }
        );
    }

    ngOnInit() {
        this.initCatalog();
    }

    searchMore(){
        console.log('scrolled');

        this.catalogService.list(this.lastItemIndex + 1, this.lastItemIndex + this.itemsPerSearch).subscribe(
            (catalog)=>{
                this.catalog.push(catalog);
                this.lastItemIndex += this.itemsPerSearch;
            },
            error => {
                this.catalog = this.catalog.concat(error);
                this.lastItemIndex += this.itemsPerSearch;
            }
        );
    }
}
