import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';
import { HomeComponent } from './home.component';
import { TenantModule } from '../tenant/tenant.module';


@NgModule({
    imports: [
        SharedModule, TenantModule
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {}