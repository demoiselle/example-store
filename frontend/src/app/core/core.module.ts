
import {
  ModuleWithProviders, NgModule,
  Optional, SkipSelf }       from '@angular/core';

import { CommonModule }      from '@angular/common';
import {AuthServiceProvider} from '@demoiselle/security';
import {HttpServiceProvider} from '@demoiselle/http';

import { CartService }       from '../cart/cart.service';


@NgModule({
  imports:      [ CommonModule ],
  declarations: [],
  exports:      [],
  providers:    [ CartService ]
})
export class CoreModule {

  constructor (@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(
        'CoreModule is already loaded. Import it in the AppModule only');
    }
  }

  static forRoot(): ModuleWithProviders {
    return {
      ngModule: CoreModule,
      providers: [
        // put here your global (singleton) services to be available for all components
    
        HttpServiceProvider({
          'endpoints': process.env.CONF.endpoints, 
          'multitenancy': process.env.CONF.multitenancy,
          'unAuthorizedRoute': '/login',
          'tokenKey' : 'id_token'
        }),
        AuthServiceProvider('~user'),
        CartService
      ]
    };
  }
}
