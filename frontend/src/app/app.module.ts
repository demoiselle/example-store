import { NgModule, ApplicationRef } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule, RequestOptions, XHRBackend, Http } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { ComponentsHelper } from 'ng2-bootstrap/ng2-bootstrap';

import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { CoreModule } from './core/core.module';
import { SecurityModule } from '@demoiselle/security';

// layout
import { SidebarMenuComponent } from './layout/sidebar-menu/sidebar-menu.component';
import { TopNavComponent } from './layout/top-nav/top-nav.component';

// feature modules
import { LoginComponent } from './login';
import { HomeModule } from './home/home.module';
import { ProdutoModule } from './produto/produto.module';
import { UsuarioModule } from './usuario';
import { CategoriaModule } from './categoria'
import { TenantModule } from './tenant';
import { ShoppingModule } from './shopping';
import { CartModule } from './cart';
import { CheckoutModule } from './checkout';

import { removeNgStyles, createNewHosts } from '@angularclass/hmr';

import {ToastModule, ToastOptions} from 'ng2-toastr/ng2-toastr';
let toastrOptions: ToastOptions = new ToastOptions({
  animate: 'flyRight',
  positionClass: 'toast-bottom-right',
  showCloseButton: true,
  dismiss: 'auto',
  toastLife: 10000
});


@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    routing,
    CoreModule.forRoot(),
    SecurityModule,

    ToastModule.forRoot(toastrOptions),

    HomeModule,
    ProdutoModule,
    UsuarioModule,
    CategoriaModule,
    TenantModule,
    ShoppingModule,
    CartModule,
    CheckoutModule
  ],
  declarations: [
    AppComponent,
    SidebarMenuComponent,
    TopNavComponent,
    LoginComponent
  ],
  providers: [{provide: ComponentsHelper, useClass: ComponentsHelper}],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(public appRef: ApplicationRef) {}
  hmrOnInit(store) {
    console.log('HMR store', store);
  }
  hmrOnDestroy(store) {
    let cmpLocation = this.appRef.components.map(cmp => cmp.location.nativeElement);
    // recreate elements
    store.disposeOldHosts = createNewHosts(cmpLocation);
    // remove styles
    removeNgStyles();
  }
  hmrAfterDestroy(store) {
    // display new elements
    store.disposeOldHosts();
    delete store.disposeOldHosts;
  }
}
