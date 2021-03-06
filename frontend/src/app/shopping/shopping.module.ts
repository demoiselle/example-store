import { NgModule } from '@angular/core';
import { SharedModule } from '../shared';
import { ShoppingRoutingModule } from './shopping-routing.module';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';
import { PaginationModule } from 'ng2-bootstrap/ng2-bootstrap';
import { InfiniteScrollModule } from 'angular2-infinite-scroll';


import { CatalogComponent } from './catalog.component';
import { CatalogService } from './catalog.service';
import { ItemPreviewComponent } from './item-preview.component';
import { DetailsComponent} from './details.component';
import { CompraComponent} from './compra.component';
import { ComprasComponent} from './compras.component';

@NgModule({
    imports: [
        SharedModule, ShoppingRoutingModule,
        ConfirmationPopoverModule.forRoot({
            confirmText: 'Sim',
            cancelText: 'Não',
            appendToBody: true
        }),
        InfiniteScrollModule
    ],
    declarations: [
        CatalogComponent,
        ItemPreviewComponent,
        DetailsComponent,
	    CompraComponent,
	    ComprasComponent

    ],
    providers: [CatalogService]
})
export class ShoppingModule { }