import { NgModule } from '@angular/core';
import { SharedModule } from '../shared';
import { DropdownModule } from 'ng2-bootstrap/ng2-bootstrap';
import { TenantCrudComponent } from './tenant-crud.component';
import { TenantListComponent } from './tenant-list.component';
import { TenantRoutingModule } from './tenant-routing.module';

import { TenantService } from './tenant.service';

import { CustomDatepickerComponent } from './custom-datepicker.component';


@NgModule({
    imports: [
        SharedModule, TenantRoutingModule,
        DropdownModule
    ],
    declarations: [
        TenantCrudComponent, TenantListComponent, CustomDatepickerComponent
    ],
    providers: [TenantService],
    exports: [TenantListComponent]
})
export class TenantModule {}