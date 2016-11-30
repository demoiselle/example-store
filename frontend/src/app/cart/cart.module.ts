import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';
import {ConfirmationPopoverModule} from 'angular-confirmation-popover';
import { ItemCartComponent } from './item-cart.component';
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
        CartComponent,
        ItemCartComponent,
        AmountSpinnerComponent
    ],
    providers: []
})
export class CartModule {}