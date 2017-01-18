import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService} from '../shared/notification.service';
import {TenantService} from './tenant.service';
import {Tenant, Cupom} from './tenant.model';
import { Usuario } from '../usuario/usuario.model';

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

  cupom: Cupom = new Cupom();

  public adminEmail:string = '';
  public adminPwd:string = '';

  scriptExample: string = 'ItemCart.addDesconto(\'desconto10\',10,false)';

  @ViewChild('staticModal') public staticModal:ModalDirective;

  constructor(private service: TenantService, private notificationService: NotificationService) {
    this.newTenant = new Tenant();
    this.cupom.name = '10AMENOS';
    this.cupom.script = 'ItemCart.addDesconto(\'desconto10\',10,false)';
    this.cupom.startDate = '2017-01-01';
    this.cupom.stopDate = '2017-12-31';
    this.cupom.sistemaId = '1';
  }
 
  ngOnInit() {

    this.service.tenantCreated.subscribe(
      () => {
        this.notificationService.error('Novo tenant criado!');
        this.list();
        return true;
      },
      (error) => {
        this.notificationService.error('Não foi possível salvar o tenant!');
        return false;
      }
    );

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
 
  save(tenant: Tenant, usuario: Usuario){
    if(this.isNewTenant)
        this.service.create(tenant, usuario);
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
    
    let usuario:Usuario = new Usuario();
    usuario.id = undefined;
    usuario.name = this.adminEmail;
    usuario.role = 'ADMINISTRATOR';
    usuario.email = this.adminEmail;
    usuario.cpf = '';
    usuario.fone = '';
    usuario.password = this.adminPwd;

    this.service.create(this.newTenant, usuario);
    
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

  saveCupom() {
      this.cupom.startDate += 'T10:00:00';
      this.cupom.stopDate += 'T00:00:00';
      console.log('CUPOM');
      console.log(this.cupom);
      this.service.createCupom(this.cupom).subscribe(
          () => {
              this.cupom.startDate = this.cupom.startDate.substr(0, 10);
              this.cupom.stopDate = this.cupom.stopDate.substr(0, 10);
              this.notificationService.success('Cupom salvo com sucesso!');
          },
          error => {
              this.cupom.startDate = this.cupom.startDate.substr(0, 10);
              this.cupom.stopDate = this.cupom.stopDate.substr(0, 10);
              this.notificationService.error('Não foi possível salvar o cupom!');
              console.log(error);
          }
      );
  }

}
