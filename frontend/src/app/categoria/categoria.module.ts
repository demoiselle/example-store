import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';

import {ConfirmationPopoverModule} from 'angular-confirmation-popover';
import { PaginationModule } from 'ng2-bootstrap/ng2-bootstrap';

import { CategoriaService } from './categoria.service';
import { CategoriaComponent } from './categoria.component';
import { CategoriaEditComponent } from './categoria-edit.component';



@NgModule({
    imports: [
        SharedModule,
        ConfirmationPopoverModule.forRoot({
            confirmText: 'Sim',
            cancelText: 'Não',
            appendToBody: true
        })
    ],
    declarations: [
        CategoriaComponent, CategoriaEditComponent
    ],
    providers: [CategoriaService]
})
export class CategoriaModule {}