import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CatalogComponent } from './catalog.component';
import { DetailsComponent } from './details.component';
import { ComprasComponent } from './compras.component';
import { CompraComponent } from './compra.component';
import {AuthGuard} from '@demoiselle/security';


@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'shopping',
                component: CatalogComponent
            },
            {
                path: 'detail/:id',
                component: DetailsComponent
            },
            {
                path: 'compras',
                canActivate: [AuthGuard],
                component: ComprasComponent
            },
            {
                path: 'compra',
                canActivate: [AuthGuard],
                component: CompraComponent
            }
        ])
    ],
    exports: [RouterModule]
})
export class ShoppingRoutingModule { }