import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TenantCrudComponent } from './tenant-crud.component';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'tenant',
                data: ['Tenants'],
                component: TenantCrudComponent
            },
        ])
    ],
    exports: [RouterModule]
})
export class TenantRoutingModule { }