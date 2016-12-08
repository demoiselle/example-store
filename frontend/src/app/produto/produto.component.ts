import { Component, OnInit, ViewChild } from '@angular/core';
import {AgRendererComponent} from 'ag-grid-ng2/main';
import {GridOptions,RowNode} from 'ag-grid/main';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';

import { NotificationService} from '../shared/notification.service';
import {ProdutoService} from './produto.service';
import {Produto} from './produto.model';

@Component({
  selector: 'dml-produto',
  templateUrl: './produto.component.html',
  styleUrls: [
    './produto.component.scss' 
  ]
})
export class ProdutoComponent implements OnInit {
  produto: Produto;
  produtos: Produto[];
  selectedProduto: Produto;
  newProduto: boolean;

  @ViewChild('staticModal') public staticModal:ModalDirective;

  private gridOptions: GridOptions;
    private showGrid: boolean;
    private rowCount: string;
    private columnDefs: any[];
  
  constructor(private service: ProdutoService, private notificationService: NotificationService) {
    this.gridOptions = <GridOptions>{};
    this.createColumnDefs();
    this.showGrid = true;``
    

  }

  createColumnDefs()  {
     this.columnDefs =[ 
       { headerName: "Id", field: "id", width:100, checkboxSelection: true},
      { headerName: "Nome", field: "name", width:200},
     { headerName: "Descrição", field: "description", width:200},
     { headerName: "Department", field: "department", width:200 },
     { headerName: "Role", field: "role", width:200 }
    ]

  }

  ngOnInit() {
    this.notificationService.info('Módulo produto iniciado!!!');

    this.service.list().subscribe(
       produtos => {
         console.log('produtos...');
         console.log(produtos);
         this.produtos = produtos
       },
       error => {
         this.produtos = error;
         //this.notificationService.error('Não foi possível carregar a lista de produtos!');
       }
     );
  }

  showModalAdd() {
    this.newProduto = true;
    this.produto = new Produto();
    this.staticModal.show();
  }
  showModalEdit() {
    this.newProduto = false;
    this.staticModal.show();
  }

  onRowSelected($event) {
    if($event.node.selected) {
      //this.produto = <Produto> $event.node.data;
      this.produto = this.cloneProduto($event.node.data);
      this.selectedProduto = $event.node.data;

    } else {
      if (this.gridOptions.api.getSelectedRows().length == 0) {
        this.produto = null;
        this.selectedProduto = null;
      }
    }
    
  }

  onRowDoubleClicked($event) {
    this.produto = this.cloneProduto($event.data);
     this.showModalEdit();
  }


  refreshGrid(){
    this.gridOptions.api.setRowData(this.gridOptions.rowData);
  }
 
  save(){
    if(this.newProduto)
            this.produtos.push(this.produto);
    else
            this.produtos[this.findSelectedProdutoIndex()] = this.produto;

      
    this.produto = null;
    this.selectedProduto = null;

    this.staticModal.hide();
    this.refreshGrid();
  }


  delete() {
    // this.service.remove(this.produto);
      this.produtos.splice(this.findSelectedProdutoIndex(), 1);
      this.produto = null;
      this.selectedProduto = null;

      this.staticModal.hide();
      this.refreshGrid();

  }

  cloneProduto(c: Produto): Produto {
        let car = new Produto();
        for(let prop in c) {
            car[prop] = c[prop];
        }
        return car;
    }
    
    findSelectedProdutoIndex(): number {
        return this.produtos.indexOf(this.selectedProduto);
    }

}
