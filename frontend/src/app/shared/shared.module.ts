import { NgModule }            from '@angular/core';
import { CommonModule }        from '@angular/common';
import { FormsModule }         from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Ng2BootstrapModule, DropdownModule } from 'ng2-bootstrap/ng2-bootstrap';
import { SecurityModule } from '@demoiselle/security';
import { NotificationService} from '../shared/notification.service';
import { LoginService } from '../login/login.service';
import { UsuarioDropdownComponent } from './top-nav/usuario-dropdown.component';
import { TenantDropdownComponent } from '../tenant/tenant-dropdown.component';

// layout
import { SidebarMenuComponent } from './sidebar-menu/sidebar-menu.component';
import { TopNavComponent } from './top-nav/top-nav.component';



@NgModule({
  imports: [CommonModule, RouterModule, DropdownModule],
  declarations: [SidebarMenuComponent, TopNavComponent, UsuarioDropdownComponent, TenantDropdownComponent],
  providers:    [
    NotificationService,
    LoginService
  ],
  exports:      [
    CommonModule, 
    FormsModule,
    RouterModule,
    Ng2BootstrapModule,
    SecurityModule,

    SidebarMenuComponent,
    TopNavComponent,
    UsuarioDropdownComponent,
    TenantDropdownComponent
  ]
})
export class SharedModule { }
