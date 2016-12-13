

import { Injectable } from '@angular/core';
import { Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Usuario } from './usuario.model';

@Injectable()
export class UsuarioService {

  constructor(private http: Http) {

  }
  list(currentPage: number, itemsPerPage: number) {

    let start = itemsPerPage * (currentPage - 1);
    //return this.http.get('~usuario/usuario/pagination/' + start + '/' + itemsPerPage + '/id/ASC')
    return this.http.get('~user/users')
      .map(
      res => res.json()

      )
      .catch(function (error) {

        return Observable.throw(<Usuario[]>[
          {
            id: 1,
            name: 'user 1 catch',
            role: 'Produto 1111111111111111111111'

          },
          {
            id: 2,
            name: 'user 2 catch',
            role: 'Produto com data 12/12/1081'

          }
        ]);
      });
  }

  get(id: number) {
    return this.http.get('~user/users/' + id)
      .map(
      res => <Usuario>res.json()

      );
  }

  create(usuario: Usuario) {
    return this.http.post('~user/users', usuario);
  }

  update(usuario: Usuario) {
    return this.http.put('~user/users/' + usuario.id, usuario);
  }

  delete(usuario: Usuario) {
    return this.http.delete('~user/users/' + usuario.id);
  }
}
