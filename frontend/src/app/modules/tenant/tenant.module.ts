import { NgModule } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Ng2BootstrapModule } from 'ng2-bootstrap';
import { DropdownModule } from 'ng2-bootstrap/ng2-bootstrap';


import { TenantComponent } from './tenant.component';
import { TenantDropdownComponent } from './tenant-dropdown.component';
import { SecurityModule } from '@demoiselle/security';
import { NotificationService} from '../../shared/notification.service';

import {AgGridModule} from 'ag-grid-ng2/main';

import {TenantService} from './tenant.service';

import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        SecurityModule,
        CommonModule, FormsModule,
        RouterModule,
        Ng2BootstrapModule, DropdownModule,
        AgGridModule.withNg2ComponentSupport()
    ],
    declarations: [
        TenantComponent, TenantDropdownComponent
    ],
    providers: [TenantService, NotificationService],
    exports: [TenantDropdownComponent]
})
export class TenantModule {}