import { NgModule }            from '@angular/core';
import { CommonModule }        from '@angular/common';
import { FormsModule }         from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Ng2BootstrapModule } from 'ng2-bootstrap/ng2-bootstrap';
import { SecurityModule } from '@demoiselle/security';
import { NotificationService} from '../shared/notification.service';
import { LoginService } from '../login/login.service';

@NgModule({
  imports:      [ CommonModule ],
  declarations: [],
  providers:    [
    NotificationService,
    LoginService
  ],
  exports:      [
    CommonModule, 
    FormsModule,
    RouterModule,
    Ng2BootstrapModule,
    SecurityModule
  ]
})
export class SharedModule { }
