import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';


import { NotificationService} from '../shared';
import {CategoriaService} from './categoria.service';
import {Categoria} from './categoria.model';

@Component({
  selector: 'dml-categoria',
  templateUrl: './categoria.component.html'
  
})
export class CategoriaComponent implements OnInit {
  categoria: Categoria;
  categorias: Categoria[];

  @ViewChild('staticModal') public staticModal:ModalDirective;

  public itemsPerPage: number = 10;
  public totalItems: number = 0;
  public currentPage: number = 1;
  
  
  constructor(private service: CategoriaService, private notificationService: NotificationService) {
  }

 
  ngOnInit() {
    this.service.list(1, 1).subscribe( // get total count by fetching only one item 
       result => {
         this.totalItems = result.total;
         this.list();
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de categorias!');
       }
     );
  }

  showModalDetails(categoria: Categoria) {
    this.categoria = categoria;
    this.staticModal.show();
  }

  pageChanged(event: any): void {
        this.currentPage = event.page;
        this.list();
  }
  
  

  list() {
    this.service.list(this.currentPage, this.itemsPerPage).subscribe(
       result => {

         this.categorias = result;
         
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de categorias!');
       }
     );
  }
 
  
  delete(categoria: Categoria) {
      this.service.delete(categoria).subscribe(
          () => {
            this.categoria = null;
            this.staticModal.hide();
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível remover a categoria!');
          }
        );

      

  }


}
