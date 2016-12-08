import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';


import { NotificationService} from '../shared';
import {UsuarioService} from './usuario.service';
import {Usuario} from './usuario.model';

@Component({
  selector: 'dml-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
  
})
export class UsuarioComponent implements OnInit {
  usuario: Usuario;
  usuarios: Usuario[];

  @ViewChild('staticModal') public staticModal:ModalDirective;

  public itemsPerPage: number = 10;
  public totalItems: number = 0;
  public currentPage: number = 1;
  
  
  constructor(private service: UsuarioService, private notificationService: NotificationService) {
  }

 
  ngOnInit() {
    this.service.list(1, 1).subscribe( // get total count by fetching only one item 
       result => {
         this.totalItems = result.total;
         this.list();
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de usuarios!');
       }
     );
  }

  showModalDetails(usuario: Usuario) {
    this.usuario = usuario;
    this.staticModal.show();
  }

  pageChanged(event: any): void {
        this.currentPage = event.page;
        this.list();
  }
  
  

  list() {
    this.service.list(this.currentPage, this.itemsPerPage).subscribe(
       result => {

         this.usuarios = result;
         
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de usuarios!');
       }
     );
  }
 
  
  delete(usuario: Usuario) {
      this.service.delete(usuario).subscribe(
          () => {
            this.usuario = null;
            this.staticModal.hide();
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível remover o usuário!');
          }
        );

      

  }

  cloneUsuario(c: Usuario): Usuario {
        let car = new Usuario();
        for(let prop in c) {
            car[prop] = c[prop];
        }
        return car;
    }

}
