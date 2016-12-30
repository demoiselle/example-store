import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CategoriaComponent } from './categoria.component';
import { CategoriaEditComponent } from './categoria-edit.component';
import { AuthGuard } from '@demoiselle/security';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'categoria',
                canActivate: [AuthGuard],
                component: CategoriaComponent
            },

            // edição de categoria
            {
                path: 'categoria/edit/:id',
                canActivate: [AuthGuard],
                component: CategoriaEditComponent
            },

            // nova categoria
            {
                path: 'categoria/edit',
                canActivate: [AuthGuard],
                component: CategoriaEditComponent
            }
        ])
    ],
    exports: [RouterModule]
})
export class CategoriaRoutingModule { }