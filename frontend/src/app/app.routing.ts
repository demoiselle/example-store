import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProdutoComponent } from './produto/produto.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { UsuarioEditComponent } from './usuario/usuario-edit.component';
import { CategoriaComponent } from './categoria/categoria.component';
import { CategoriaEditComponent } from './categoria/categoria-edit.component';
import { TenantCrudComponent } from './tenant/tenant-crud.component';
import { CatalogComponent } from './shopping/catalog.component';
import { DetailsComponent } from './shopping/details.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';

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
    path: 'categoria', 
    data: ['Categoria'],
    canActivate: [AuthGuard],
    component: CategoriaComponent
  },

  // edição de categoria
  { 
    path: 'categoria/edit/:id',
    canActivate: [AuthGuard],
    component: CategoriaEditComponent 
  },

  // nova categoria
  { 
    path: 'categoria/edit',
    canActivate: [AuthGuard],
    component: CategoriaEditComponent 
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
  { 
    path: 'checkout', 
    component: CheckoutComponent
  },


];

export const routing = RouterModule.forRoot(routes, {useHash: true});
