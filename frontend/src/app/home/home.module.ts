import { NgModule } from '@angular/core';
import {SharedModule} from '../shared';
import { HomeComponent } from './home.component';
import { TenantModule } from '../tenant/tenant.module';
import { HomeRoutingModule } from './home-routing.module';


@NgModule({
    imports: [
        SharedModule, TenantModule, HomeRoutingModule
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {}