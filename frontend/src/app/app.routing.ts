import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProdutoComponent } from './produto/produto.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { UsuarioEditComponent } from './usuario/usuario-edit.component';
import { TenantCrudComponent } from './tenant/tenant-crud.component';
import { CatalogComponent } from './shopping/catalog.component';
import { DetailsComponent } from './shopping/details.component';
import { CartComponent } from './shopping/cart.component';

import {AuthGuard} from '@demoiselle/security';
//import {AuthGuard} from '@demoiselle/security'; // when importing from npm repository

export const routes: Routes = [
  { 
    path: 'login', 
    component: LoginComponent
  },
  { 
    path: '',
    data: ['Home'], 
    component: HomeComponent
  },
  { 
    path: 'usuario', 
    data: ['Usuário'],
    canActivate: [AuthGuard],
    component: UsuarioComponent
  },

  // edição de usuário
  { 
    path: 'usuario/edit/:id',
    canActivate: [AuthGuard],
    component: UsuarioEditComponent 
  },

  // novo usuário
  { 
    path: 'usuario/edit',
    canActivate: [AuthGuard],
    component: UsuarioEditComponent 
  },
  { 
    path: 'tenant', 
    data: ['Tenants'],
    canActivate: [AuthGuard],
    component: TenantCrudComponent
  },

  { 
    path: 'shopping', 
    component: CatalogComponent
  },
  { 
    path: 'detail/:id', 
    component: DetailsComponent
  },
  { 
    path: 'cart', 
    component: CartComponent
  },


];

export const routing = RouterModule.forRoot(routes, {useHash: true});
