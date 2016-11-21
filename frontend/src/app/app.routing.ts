import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './modules/home/home.component';
import { AboutComponent } from './modules/about/about.component';
import { LoginComponent } from './modules/login/login.component';
import { ProdutoComponent } from './modules/produto/produto.component';
import { UsuarioComponent } from './modules/usuario/usuario.component';
import { UsuarioEditComponent } from './modules/usuario/usuario-edit.component';
import { TenantComponent } from './modules/tenant/tenant.component';

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
    component: HomeComponent,
    canActivate: [AuthGuard] 
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
    component: TenantComponent
  }
];

export const routing = RouterModule.forRoot(routes, {useHash: true});
