import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import {SecurityModule} from '@demoiselle/security';

@NgModule({
    imports: [
        SecurityModule
    ],
    declarations: [
        HomeComponent
    ]
})
export class HomeModule {}