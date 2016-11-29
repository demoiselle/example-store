import { NgModule } from '@angular/core';
import { SharedModule } from '../shared';
import { DropdownModule } from 'ng2-bootstrap/ng2-bootstrap';
import { TenantCrudComponent } from './tenant-crud.component';
import { TenantDropdownComponent } from './tenant-dropdown.component';
import { TenantListComponent } from './tenant-list.component';

import {TenantService} from './tenant.service';


@NgModule({
    imports: [
        SharedModule,
        DropdownModule
    ],
    declarations: [
        TenantCrudComponent, TenantDropdownComponent, TenantListComponent
    ],
    providers: [TenantService],
    exports: [TenantDropdownComponent, TenantListComponent]
})
export class TenantModule {}