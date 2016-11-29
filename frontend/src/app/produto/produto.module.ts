import { NgModule } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Ng2BootstrapModule } from 'ng2-bootstrap';

import { ProdutoComponent } from './produto.component';
import { SecurityModule } from '@demoiselle/security';
import { NotificationService} from '../shared/notification.service';

import {AgGridModule} from 'ag-grid-ng2/main';

import {ProdutoService} from './produto.service';


@NgModule({
    imports: [
        SecurityModule,
        CommonModule, FormsModule,
        Ng2BootstrapModule,
        AgGridModule.withNg2ComponentSupport()
    ],
    declarations: [
        ProdutoComponent
    ],
    providers: [ProdutoService, NotificationService]
})
export class ProdutoModule {}