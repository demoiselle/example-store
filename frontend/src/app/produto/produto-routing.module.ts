import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProdutoComponent } from './produto.component';
import { ProdutoEditComponent } from './produto-edit.component';

import { AuthGuard } from '@demoiselle/security';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'produto',
                canActivate: [AuthGuard],
                component: ProdutoComponent
            },

            // edição de produto
            {
                path: 'produto/edit/:id',
                canActivate: [AuthGuard],
                component: ProdutoEditComponent
            },

            // novo produto
            {
                path: 'produto/edit',
                canActivate: [AuthGuard],
                component: ProdutoEditComponent
            }
        ])
    ],
    exports: [RouterModule]
})
export class ProdutoRoutingModule { }