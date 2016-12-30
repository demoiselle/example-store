import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';

@NgModule({
    imports: [
        RouterModule.forRoot([
            /* define app module routes here, e.g., to lazily load a module
               (do not place feature module routes here, use an own -routing.module.ts in the feature instead)
             */

        ], {useHash: true})
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }