import { NgModule } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared';
import { ProdutoComponent } from './produto.component';
import { ProdutoEditComponent } from './produto-edit.component';
import { SecurityModule } from '@demoiselle/security';
import { NotificationService} from '../shared/notification.service';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';

import {ProdutoService} from './produto.service';


@NgModule({
    imports: [
        SharedModule, SecurityModule,
        CommonModule, FormsModule,
        ConfirmationPopoverModule.forRoot({
            confirmText: 'Sim',
            cancelText: 'Não',
            appendToBody: true
        })
    ],
    declarations: [
        ProdutoComponent, ProdutoEditComponent
    ],
    providers: [ProdutoService, NotificationService]
})
export class ProdutoModule {}