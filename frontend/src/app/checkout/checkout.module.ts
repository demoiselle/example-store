import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';
import {ConfirmationPopoverModule} from 'angular-confirmation-popover';
import { ItemCartComponent } from './item-cart.component';
import { CheckoutComponent} from './checkout.component';
import { CheckoutService } from './checkout.service';


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
        CheckoutComponent,
        ItemCartComponent

    ],
    providers: [CheckoutService]
})
export class CheckoutModule {}