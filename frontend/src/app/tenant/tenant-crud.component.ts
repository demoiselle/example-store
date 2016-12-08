import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService} from '../shared/notification.service';
import {TenantService} from './tenant.service';
import {Tenant} from './tenant.model';

@Component({
  selector: 'dml-tenant',
  templateUrl: './tenant-crud.component.html',
  styleUrls: ['./tenant-crud.component.scss']
})
export class TenantCrudComponent implements OnInit {
  tenant: Tenant;
  tenants: Tenant[];
  selectedTenant: Tenant;
  isNewTenant: boolean;
  newTenant:Tenant;
  viewOnly: boolean;

  scriptExample: string = 'usuario.setNome(tenant.getName() + " - " + usuario.getNome());';

  @ViewChild('staticModal') public staticModal:ModalDirective;

  
  constructor(private service: TenantService, private notificationService: NotificationService) {
    this.newTenant = new Tenant();
  }

 
  ngOnInit() {
    this.notificationService.info('Módulo tenant iniciado!!!');

    this.list();
  }

  // showModalAdd() {
  //   this.viewOnly = false;
  //   this.isNewTenant = true;
  //   this.tenant = new Tenant();
  //   this.staticModal.show();
  // }
  // showModalEdit() {
  //   this.viewOnly = false;
  //   this.isNewTenant = false;
  //   this.staticModal.show();
  // }
  showModalDetail(tenant: Tenant) {
    this.viewOnly = true;
    this.tenant = tenant;    
    this.isNewTenant = false;
    this.staticModal.show();
  }

  list() {
    this.service.list().subscribe(
       tenants => {
         this.tenants = tenants;
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de tenants!');
       }
     );
  }
 
  save(){
    if(this.isNewTenant)
        this.service.create(this.newTenant).subscribe(
          () => {
            this.tenant = null;
            this.selectedTenant = null;
            this.staticModal.hide();
            
            this.list();
            this.notificationService.success('Tenant salvo!');
          },
          error => {
            this.notificationService.error('Não foi possível salvar o tenant!');
            console.log('ERROR');
            console.log(error);
            this.list(); // remover depois que estiver retornando sucesso
          }
        );
    else {
        //this.tenants[this.findSelectedTenantIndex()] = this.tenant;
        this.service.update(this.tenant).subscribe(
          tenants => {
            this.tenant = null;
            this.selectedTenant = null;
            this.staticModal.hide();
            
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o tenant!');
          }
        );
    }
  }

  addTenant() {
    this.isNewTenant = true;
    this.save();
    this.newTenant = new Tenant();
  }

  deleteTenant(tenant: Tenant) {
      
      this.service.delete(tenant).subscribe(
          () => {
            this.tenant = null;
            this.selectedTenant = null;
            this.staticModal.hide();
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível remover o tenant!');
          }
        );
  }


  cloneTenant(c: Tenant): Tenant {
        let car = new Tenant();
        for(let prop in c) {
            car[prop] = c[prop];
        }
        return car;
    }

}
