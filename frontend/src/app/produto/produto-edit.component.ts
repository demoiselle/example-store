import { Component, OnInit, ViewChild } from '@angular/core';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';
import { Router, ActivatedRoute } from '@angular/router';

import { NotificationService} from '../shared';
import {ProdutoService} from './produto.service';
import {Produto} from './produto.model';
import { Categoria } from '../categoria/categoria.model';
import { CategoriaService } from '../categoria/categoria.service';

@Component({
  selector: 'dml-produto-edit',
  templateUrl: './produto-edit.component.html'
})
export class ProdutoEditComponent implements OnInit {
  produto: Produto;
  id: number;
  produtoLoaded: boolean = false;
  private routeSubscribe: any;
  categorias: Categoria[];
  
  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private service: ProdutoService, 
      private categoriaService: CategoriaService,
      private notificationService: NotificationService) {
  }

 
  ngOnInit() {
      this.routeSubscribe = this.route.params.subscribe(params => {
          if(params['id']) {
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.loadProduto();
          } else {
              this.produto = new Produto();
              this.produtoLoaded = true;
          }
       });

       this.categoriaService.list(0, 1000).subscribe(
          result => {
              this.categorias = result;
          },
          error => {
            this.notificationService.error('Não foi possível carregar a lista de categorias!');
          }
        );       
  }

  ngOnDestroy() {
    this.routeSubscribe.unsubscribe();
  }

  onChangeCategoria(categoria) {
    
    this.produto.category = categoria;
    
  }


  loadProduto() {


      this.service.get(this.id)
        .subscribe(
            (produto: Produto) => {
                this.produto = produto;
                this.produtoLoaded = true;
            },
            error => {
                this.notificationService.error('Erro ao carregar produto');
            }
        );
  }

  save() {
      
    if(!this.id) {
        this.service.create(this.produto).subscribe(
          () => {
            this.notificationService.success('Produto criado com sucesso!');
            this.back();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o produto!');
          }
        );
    } else {
        this.service.update(this.produto).subscribe(
          produto => {
            this.notificationService.success('Produto atualizado com sucesso!');
            this.back();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o produto!');
          }
        );
    }
   
  }

  back() {
        this.router.navigate(['/produto']);
    }
}
