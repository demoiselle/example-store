import { NgModule } from '@angular/core';
import { SharedModule } from '../shared';
import { UsuarioRoutingModule } from './usuario-routing.module';

import { ConfirmationPopoverModule } from 'angular-confirmation-popover';

import { UsuarioService } from './usuario.service';
import { UsuarioComponent } from './usuario.component';
import { UsuarioEditComponent } from './usuario-edit.component';

import { ContaComponent } from './conta.component';
import { ContaService } from './conta.service';


@NgModule({
    imports: [
        SharedModule,
        UsuarioRoutingModule,
        ConfirmationPopoverModule.forRoot({
            confirmText: 'Sim',
            cancelText: 'Não',
            appendToBody: true
        })
    ],
    declarations: [
        UsuarioComponent, UsuarioEditComponent, ContaComponent
    ],
    providers: [
        UsuarioService,
        ContaService
    ],
    exports: [
        ContaComponent
    ]
})
export class UsuarioModule { }
