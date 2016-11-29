import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';
import {ConfirmationPopoverModule} from 'angular-confirmation-popover';
import { PaginationModule } from 'ng2-bootstrap/ng2-bootstrap';

import { CatalogComponent } from './catalog.component';
import { CatalogService } from './catalog.service';
import { ItemPreviewComponent } from './item-preview.component';
import { ItemCartComponent } from './item-cart.component';
import { DetailsComponent} from './details.component';
import { CartComponent} from './cart.component';
import { AmountSpinnerComponent} from './amount-spinner.component';


@NgModule({
    imports: [
        SharedModule,
        ConfirmationPopoverModule.forRoot({
            confirmText: 'Sim',
            cancelText: 'Não',
            appendToBody: true
        })
    ],
    declarations: [
        CatalogComponent,
        ItemPreviewComponent,
        DetailsComponent,
        CartComponent,
        ItemCartComponent,
        AmountSpinnerComponent
    ],
    providers: [CatalogService]
})
export class ShoppingModule {}