import { NgModule } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { Ng2BootstrapModule } from 'ng2-bootstrap';
import { PaginationModule } from 'ng2-bootstrap/ng2-bootstrap';

import { UsuarioComponent } from './usuario.component';
import { UsuarioEditComponent } from './usuario-edit.component';
import { SecurityModule } from '@demoiselle/security';
import { NotificationService} from '../../shared/notification.service';

import {AgGridModule} from 'ag-grid-ng2/main';

import {UsuarioService} from './usuario.service';


@NgModule({
    imports: [
        SecurityModule,
        CommonModule, FormsModule, RouterModule,
        Ng2BootstrapModule,
        AgGridModule.withNg2ComponentSupport()
    ],
    declarations: [
        UsuarioComponent, UsuarioEditComponent
    ],
    providers: [UsuarioService, NotificationService]
})
export class UsuarioModule {}