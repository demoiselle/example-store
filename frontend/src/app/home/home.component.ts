import { Component, OnInit } from '@angular/core';
import {Tenant, ITenant} from '../tenant/tenant.model';
import {TenantService} from '../tenant/tenant.service';
import { NotificationService} from '../shared/notification.service';

@Component({
  selector: 'dml-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  protected tenant:ITenant;

  constructor(protected service: TenantService, protected notificationService: NotificationService) {
  }

  ngOnInit() {
    this.tenant = new Tenant();
    console.log('Hello Home');
  }

  save(){
    this.service.create(this.tenant).subscribe(
      () => {
        this.tenant = new Tenant();
        this.notificationService.success('Nova loja salva com sucesso!');
      },
      (error) => {
        this.notificationService.error('Não foi possível salvar a loja!');
      }
    );
  }
}
