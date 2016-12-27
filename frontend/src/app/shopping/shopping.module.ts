import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';
import {ConfirmationPopoverModule} from 'angular-confirmation-popover';
import { PaginationModule } from 'ng2-bootstrap/ng2-bootstrap';
import { InfiniteScrollModule } from 'angular2-infinite-scroll';


import { CatalogComponent } from './catalog.component';
import { CatalogService } from './catalog.service';
import { ItemPreviewComponent } from './item-preview.component';
import { DetailsComponent} from './details.component';
import { ComprasComponent} from './compras.component';

@NgModule({
    imports: [
        SharedModule,
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
        ComprasComponent
    ],
    providers: [CatalogService]
})
export class ShoppingModule {}