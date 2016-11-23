import { Component, OnInit, ViewChild } from '@angular/core';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';
import { Router, ActivatedRoute } from '@angular/router';

import { NotificationService} from '../../shared/notification.service';
import {UsuarioService} from './usuario.service';
import {Usuario, IUsuario} from './usuario.model';

@Component({
  selector: 'dml-usuario-edit',
  templateUrl: './usuario-edit.component.html'
})
export class UsuarioEditComponent implements OnInit {
  usuario: IUsuario;
  id: number;
  usuarioLoaded: boolean = false;
  private routeSubscribe: any;
  
  
  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private service: UsuarioService, 
      private notificationService: NotificationService) {
  }

 
  ngOnInit() {
      this.routeSubscribe = this.route.params.subscribe(params => {
          if(params['id']) {
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.loadUsuario();
          } else {
              this.usuario = new Usuario();
              this.usuarioLoaded = true;
          }
       });
  }

  ngOnDestroy() {
    this.routeSubscribe.unsubscribe();
  }


  loadUsuario() {


      this.service.get(this.id)
        .subscribe(
            (usuario: Usuario) => {
                this.usuario = usuario;
                this.usuarioLoaded = true;
            },
            error => {
                this.notificationService.error('Erro ao carregar usuário');
            }
        );
  }

  save() {
      
    if(!this.id)
        this.service.create(this.usuario).subscribe(
          () => {
            this.notificationService.error('Usuário criado com sucesso!');
            this.back();
          },
          error => {
            this.notificationService.success('Não foi possível salvar o usuário!');
          }
        );
    else {
        this.service.update(this.usuario).subscribe(
          usuario => {
            this.notificationService.success('Usuário atualizado com sucesso!');
            this.back();
          },
          error => {
            this.notificationService.error('Não foi possível salvar o usuário!');
          }
        );
    }
   
  }

  back() {
        this.router.navigate(['/usuario']);
    }
}
