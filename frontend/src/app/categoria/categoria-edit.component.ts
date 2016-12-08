import { Component, OnInit, ViewChild } from '@angular/core';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';
import { Router, ActivatedRoute } from '@angular/router';

import { NotificationService} from '../shared';
import {CategoriaService} from './categoria.service';
import {Categoria} from './categoria.model';

@Component({
  selector: 'dml-categoria-edit',
  templateUrl: './categoria-edit.component.html'
})
export class CategoriaEditComponent implements OnInit {
  categoria: Categoria;
  id: number;
  categoriaLoaded: boolean = false;
  private routeSubscribe: any;
  
  
  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private service: CategoriaService, 
      private notificationService: NotificationService) {
  }

 
  ngOnInit() {
      this.routeSubscribe = this.route.params.subscribe(params => {
          if(params['id']) {
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.loadCategoria();
          } else {
              this.categoria = new Categoria();
              this.categoriaLoaded = true;
          }
       });
  }

  ngOnDestroy() {
    this.routeSubscribe.unsubscribe();
  }


  loadCategoria() {


      this.service.get(this.id)
        .subscribe(
            (categoria: Categoria) => {
                this.categoria = categoria;
                this.categoriaLoaded = true;
            },
            error => {
                this.notificationService.error('Erro ao carregar categoria');
            }
        );
  }

  save() {
      
    if(!this.id)
        this.service.create(this.categoria).subscribe(
          () => {
            this.notificationService.success('Categoria criado com sucesso!');
            this.back();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o categoria!');
          }
        );
    else {
        this.service.update(this.categoria).subscribe(
          categoria => {
            this.notificationService.success('Categoria atualizado com sucesso!');
            this.back();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o categoria!');
          }
        );
    }
   
  }

  back() {
        this.router.navigate(['/categoria']);
    }
}
