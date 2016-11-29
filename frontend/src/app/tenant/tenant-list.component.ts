import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService} from '../shared/notification.service';
import {TenantService} from './tenant.service';
import {Tenant, ITenant} from './tenant.model';

@Component({
  selector: 'dml-tenant-list',
  templateUrl: './tenant-list.component.html'
})
export class TenantListComponent implements OnInit {
    tenants: Array<Tenant>;
    selectedTenant: Tenant;

    ngOnInit() {
        this.selectedTenant = this.service.getSelectedTenant();
        this.loadTenants();
    }

    constructor(private service: TenantService, private notificationService: NotificationService, private router: Router) {
        service.tenantsChanged.subscribe(
            (tenant) => {
                this.loadTenants();
            }
        );
    }

    loadTenants() {
        this.service.list().subscribe(
            tenants => {
                this.tenants = tenants;
            },
            error => {
                this.notificationService.error('Não foi possível carregar a lista de tenants!');
            }
        );
    }

    selectTenant(tenant:Tenant) {
        this.service.selectTenant(tenant);
        this.selectedTenant = tenant;
        //this.notificationService.success('Tenant <' + tenant.name + '> selecionado com sucesso!');
        this.router.navigate(['/shopping']);
    }
}