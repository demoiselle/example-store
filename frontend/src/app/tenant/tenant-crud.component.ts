import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AgRendererComponent } from 'ag-grid-ng2/main';
import { GridOptions,RowNode } from 'ag-grid/main';
import { ModalDirective } from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService} from '../shared/notification.service';
import { TenantService} from './tenant.service';
import { Tenant, Cupom } from './tenant.model';
import { Usuario } from '../usuario/usuario.model';

import { DatePickerComponent } from 'ng2-bootstrap/ng2-bootstrap';
import { CustomDatepickerComponent } from '../shared/custom-datepicker.component';

@Component({
  selector: 'dml-tenant',
  templateUrl: './tenant-crud.component.html',
  styleUrls: ['./tenant-crud.component.scss']
})
export class TenantCrudComponent implements OnInit {
  tenants: Tenant[];
  cupom: Cupom = new Cupom();
  scriptExample: string = 'ItemCart.addDesconto(\'desconto10\',10,false)';
  cupomForm: FormGroup;

  @ViewChild('staticModal') public staticModal: ModalDirective;
  @ViewChild('startDate') public startDate: CustomDatepickerComponent;
  @ViewChild('endDate') public endDate: CustomDatepickerComponent;

  constructor(private service: TenantService,
    private notificationService: NotificationService,
    private formBuilder: FormBuilder)
  {
    this.cupom.name = '10AMENOS';
    this.cupom.script = 'ItemCart.addDesconto(\'desconto10\',10,false)';
    this.cupom.startDate = '2017-01-01';
    this.cupom.stopDate = '2017-12-31';
    this.cupom.sistemaId = '1';
  }
 
  ngOnInit() {
    this.cupomForm = this.formBuilder.group({
        rfCupomName: ['', Validators.required],
        rfStartDate: new FormControl(),
        rfStopDate: new FormControl(),
        rfScript: ['', Validators.required]
    });

    this.service.tenantCreated.subscribe(
      () => {
        this.notificationService.success('Novo tenant criado!');
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

  showModalDetail(tenant: Tenant) {
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
 
  deleteTenant(tenant: Tenant) {
      this.service.delete(tenant).subscribe(
          () => {
            this.staticModal.hide();
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível remover o tenant!');
          }
        );
  }


  saveCupom() {
      // TODO
      // Fazer a validação usando o angular, trocando dados com o
      // datepicker customizado
      if (!this.startDate.datePickerValue || !this.endDate.datePickerValue) {
        this.notificationService.error('Preencha as datas de início e fim!');
        return;
      }
      if (this.startDate.datePickerValue > this.endDate.datePickerValue) {
        this.notificationService.error('A data de encerramento é anterior a data de início!');
        return;
      }

      this.cupom.startDate = this.startDate.datePickerValue.toISOString().slice(0,10);
      this.cupom.stopDate = this.endDate.datePickerValue.toISOString().slice(0,10);

      this.cupom.startDate += 'T10:00:00';
      this.cupom.stopDate += 'T00:00:00';

      console.log('CUPOM');
      console.log(this.cupom);

      this.service.createCupom(this.cupom).subscribe(
          () => {
              this.notificationService.success('Cupom salvo com sucesso!');
          },
          error => {
              this.notificationService.error('Não foi possível salvar o cupom!');
              console.log(error);
          }
      );
  }

}
