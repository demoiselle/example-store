import { Component, OnInit } from '@angular/core';
import { TenantService } from '../tenant/tenant.service';
import { Tenant } from '../tenant/tenant.model';
import { NotificationService } from '../shared/notification.service';
import { Usuario } from '../usuario/usuario.model';

@Component({
  selector: 'dml-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public tenantName:string = '';
  public adminEmail:string = '';
  public adminPwd:string = '';

  constructor(protected tenantService: TenantService, protected notificationService: NotificationService) {
  }

  ngOnInit() {
    console.log('Hello Home');
    this.tenantService.tenantCreated.subscribe(
      () => {
        this.notificationService.success('Nova loja criada!');
        return true;
      },
      (error) => {
        this.notificationService.error('Não foi possível salvar a loja!');
        return false;
      }
    );
  }

  addTenant() {
    let tenant = new Tenant();
    tenant.name = this.tenantName;

    let usuario:Usuario = new Usuario();
    usuario.id = undefined;
    usuario.name = this.adminEmail;
    usuario.role = 'ADMINISTRATOR';
    usuario.email = this.adminEmail;
    usuario.cpf = '';
    usuario.fone = '';
    usuario.password = this.adminPwd;

    this.tenantService.create(tenant, usuario);
  }
}
