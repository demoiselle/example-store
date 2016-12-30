import { NgModule } from '@angular/core';
import { SharedModule } from '../shared';
import { ProdutoRoutingModule } from './produto-routing.module';

import { ProdutoComponent } from './produto.component';
import { ProdutoEditComponent } from './produto-edit.component';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';

import {ProdutoService} from './produto.service';


@NgModule({
    imports: [
        SharedModule, ProdutoRoutingModule,
        ConfirmationPopoverModule.forRoot({
            confirmText: 'Sim',
            cancelText: 'Não',
            appendToBody: true
        })
    ],
    declarations: [
        ProdutoComponent, ProdutoEditComponent
    ],
    providers: [ProdutoService]
})
export class ProdutoModule {}