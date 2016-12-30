import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UsuarioComponent } from './usuario.component';
import { UsuarioEditComponent } from './usuario-edit.component';
import { AuthGuard } from '@demoiselle/security';
import { ContaComponent } from './conta.component';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'usuario',
                canActivate: [AuthGuard],
                data: ['Usuário'],
                component: UsuarioComponent
            },

            // edição de usuário
            {
                path: 'usuario/edit/:id',
                canActivate: [AuthGuard],
                component: UsuarioEditComponent
            },

            // novo usuário
            {
                path: 'usuario/edit',
                // canActivate: [AuthGuard],
                component: UsuarioEditComponent
            },

            {
                path: 'conta',
                canActivate: [AuthGuard],
                component: ContaComponent
            },
        ])
    ],
    exports: [RouterModule]
})
export class UsuarioRoutingModule { }