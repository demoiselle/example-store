import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CheckoutComponent } from './checkout.component';
import { AuthGuard } from '@demoiselle/security';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'checkout',
                canActivate: [AuthGuard],
                component: CheckoutComponent
            }
        ])
    ],
    exports: [RouterModule]
})
export class CheckoutRoutingModule { }