import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';


import { NotificationService} from '../shared';
import {ProdutoService} from './produto.service';
import {Produto} from './produto.model';

@Component({
  selector: 'dml-produto',
  templateUrl: './produto.component.html'
    
})
export class ProdutoComponent implements OnInit {
  produto: Produto;
  produtos: Produto[];

  @ViewChild('staticModal') public staticModal:ModalDirective;

  public itemsPerPage: number = 10;
  public totalItems: number = 0;
  public currentPage: number = 1;
  
  
  constructor(private service: ProdutoService, private notificationService: NotificationService) {
  }

 
  ngOnInit() {
    this.service.list(1, 1).subscribe( // get total count by fetching only one item 
       result => {
         this.totalItems = result.total;
         this.list();
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de produtos!');
       }
     );
  }

  showModalDetails(produto: Produto) {
    this.produto = produto;
    this.staticModal.show();
  }

  pageChanged(event: any): void {
        this.currentPage = event.page;
        this.list();
  }
  
  

  list() {
    this.service.list(this.currentPage, this.itemsPerPage).subscribe(
       result => {

         this.produtos = result;
         
       },
       error => {
         this.notificationService.error('Não foi possível carregar a lista de produtos!');
       }
     );
  }
 
  
  delete(produto: Produto) {
      this.service.delete(produto).subscribe(
          () => {
            this.produto = null;
            this.staticModal.hide();
            this.list();
          },
          error => {
            this.notificationService.error('Não foi possível remover o produto!');
          }
        );

      

  }

  cloneProduto(c: Produto): Produto {
        let car = new Produto();
        for(let prop in c) {
            car[prop] = c[prop];
        }
        return car;
    }

}
