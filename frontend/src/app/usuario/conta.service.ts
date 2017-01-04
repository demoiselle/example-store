import { Injectable, EventEmitter } from '@angular/core';
import { Http } from '@angular/http';

import { Usuario } from './usuario.model';

@Injectable()
export class ContaService {

  public usuarioCarregado: EventEmitter<Usuario>;
  protected usuario;

  constructor(protected http: Http) {
    this.usuarioCarregado = new EventEmitter<Usuario>();
  }

  getLoggeduser() {
    return this.http.get('~user/auth/user')
      .map(
        res => <Usuario>res.json()
      );
  }

  getUserByEmail(email: string) {
    return this.http.get('~user/users/usuario/' + email)
      .map(
        res => <Usuario>res.json()
      );
  }

  loadUserByEmail(email: string) {
    if (!this.usuario) {
      this.http.get('~user/users/usuario/' + email)
        .map(
          res => <Usuario>res.json()
        ).subscribe( 
          (result) => {
            console.log('LOAD RESULT');
            console.log(result);
            this.usuario = result;
            this.usuarioCarregado.emit(this.usuario);
          },
          (error) => {
            this.usuario = null;
            console.log('LOAD ERROR');
            console.log(error);
            this.usuario = null;
            this.usuarioCarregado.emit(null);
          }
        );
     } else {
        this.usuarioCarregado.emit(this.usuario);
     }
   }

}