import { NgModule, ApplicationRef } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule, RequestOptions, XHRBackend, Http } from '@angular/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { CoreModule } from './core/core.module';

// layout
import { SidebarMenuComponent } from './layout/sidebar-menu/sidebar-menu.component';
import { TopNavComponent } from './layout/top-nav/top-nav.component';

// feature modules
import { LoginComponent } from './login';
import { HomeModule } from './home/home.module';
import { ProdutoModule } from './produto/produto.module';
import { UsuarioModule } from './usuario';
import { TenantModule } from './tenant';
import { ShoppingModule } from './shopping';

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

    ToastModule.forRoot(toastrOptions),

    HomeModule,
    ProdutoModule,
    UsuarioModule,
    TenantModule,
    ShoppingModule
  ],
  declarations: [
    AppComponent,
    SidebarMenuComponent,
    TopNavComponent,
    LoginComponent
  ],
  providers: [],
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
