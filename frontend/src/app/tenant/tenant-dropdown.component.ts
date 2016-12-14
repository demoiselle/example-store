import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AgRendererComponent } from 'ag-grid-ng2/main';
import { GridOptions, RowNode } from 'ag-grid/main';
import { ModalDirective } from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService } from '../shared/notification.service';
import { TenantService } from './tenant.service';
import { Tenant } from './tenant.model';

import { CartService } from '../cart/cart.service';

@Component({
    selector: 'dml-tenant-dropdown',
    templateUrl: './tenant-dropdown.component.html'
})
export class TenantDropdownComponent implements OnInit {
    tenants: Array<Tenant>;
    selectedTenant: Tenant;
    hasTenants: Boolean;

    ngOnInit() {
        this.hasTenants = false;
        this.selectedTenant = this.service.getSelectedTenant();
        this.loadTenants();
    }

    constructor(
        private service: TenantService,
        private notificationService: NotificationService,
        private cartService: CartService,
        private router: Router) {
        service.tenantsChanged.subscribe(
            (tenant) => {
                this.loadTenants();
            }
        );
        service.tenantChanged.subscribe(
            (tenant) => {
                this.selectedTenant = tenant;
            }
        );
    }

    loadTenants() {
        this.service.list().subscribe(
            tenants => {
                this.tenants = tenants;
                this.hasTenants = true;
            },
            error => {
                this.notificationService.error('Não foi possível carregar a lista de tenants!');
                this.hasTenants = false;
            }
        );
    }

    selectTenant(tenant: Tenant) {
        if (tenant.name == this.selectedTenant.name) {
            return;
        }
        this.service.selectTenant(tenant);
        this.selectedTenant = tenant;
        this.notificationService.success('Tenant <' + tenant.name + '> selecionado com sucesso!');
        this.cartService.clearCart();
        this.router.navigate(['/shopping']);
    }
}
