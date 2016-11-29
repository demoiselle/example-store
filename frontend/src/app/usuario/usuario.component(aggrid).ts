import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService} from '../../shared/notification.service';
import {UsuarioService} from './usuario.service';
import {Usuario, IUsuario} from './usuario.model';

@Component({
  selector: 'dml-usuario',
  templateUrl: './usuario.component.html'
})
export class UsuarioComponent implements OnInit {
  usuario: IUsuario;
  usuarios: IUsuario[];
  selectedUsuario: IUsuario;
  newUsuario: boolean;

  @ViewChild('staticModal') public staticModal:ModalDirective;

  private gridOptions: GridOptions;
    private showGrid: boolean;
    private rowCount: string;
    private columnDefs: any[];
  
  constructor(private service: UsuarioService, private notificationService: NotificationService) {
    this.gridOptions = <GridOptions>{};
    this.createColumnDefs();
    this.showGrid = true;``
    

  }

  createColumnDefs()  {
     this.columnDefs =[ 
       { headerName: "Id", field: "id", width:100, checkboxSelection: true},
       { headerName: "Nome", field: "nome", width:200},
       { headerName: "Perfil", field: "perfil", width:200}
    ]

  }

  ngOnInit() {
    this.notificationService.info('Módulo usuario iniciado!!!');

    this.list();
  }

  showModalAdd() {
    this.newUsuario = true;
    this.usuario = new Usuario();
    this.staticModal.show();
  }
  showModalEdit() {
    this.newUsuario = false;
    this.staticModal.show();
  }

  onRowSelected($event) {
    if($event.node.selected) {
      //this.usuario = <Usuario> $event.node.data;
      this.usuario = this.cloneUsuario($event.node.data);
      this.selectedUsuario = $event.node.data;

    } else {
      if (this.gridOptions.api.getSelectedRows().length == 0) {
        this.usuario = null;
        this.selectedUsuario = null;
      }
    }
    
  }

  onRowDoubleClicked($event) {
    this.usuario = this.cloneUsuario($event.data);
     this.showModalEdit();
  }


  refreshGrid(){
    this.gridOptions.api.setRowData(this.gridOptions.rowData);
  }

  list() {
    this.service.list().subscribe(
       usuarios => {
         this.usuarios = usuarios;
         this.refreshGrid();
       },
       error => {
         this.usuarios = error;
         //this.notificationService.error('Não foi possível carregar a lista de usuarios!');
       }
     );
  }
 
  save(){
    if(this.newUsuario)
            //this.usuarios.push(this.usuario);
        this.service.create(this.usuario).subscribe(
          usuarios => {
            this.usuario = null;
            this.selectedUsuario = null;
            this.staticModal.hide();
            
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o usuário!');
          }
        );
    else {
        //this.usuarios[this.findSelectedUsuarioIndex()] = this.usuario;
        this.service.update(this.usuario).subscribe(
          usuarios => {
            this.usuario = null;
            this.selectedUsuario = null;
            this.staticModal.hide();
            
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o usuário!');
          }
        );
    }

      
    
  }


  delete() {
      //this.usuarios.splice(this.findSelectedUsuarioIndex(), 1);

      this.service.delete(this.usuario).subscribe(
          () => {
            this.usuario = null;
            this.selectedUsuario = null;
            this.staticModal.hide();
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível remover o usuário!');
          }
        );

      

  }

  cloneUsuario(c: IUsuario): IUsuario {
        let car = new Usuario();
        for(let prop in c) {
            car[prop] = c[prop];
        }
        return car;
    }

}
