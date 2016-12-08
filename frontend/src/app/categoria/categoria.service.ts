

import { Injectable } from '@angular/core';
import { Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { ICategoria, Categoria } from './categoria.model';

@Injectable()
export class CategoriaService {

  constructor(private http: Http) {

  }
  list(currentPage: number, itemsPerPage: number) {

    let start = itemsPerPage * (currentPage - 1);
    return this.http.get('~product/categories')
      .map(
      res => res.json()

      );
  }

  get(id: number) {
    return this.http.get('~product/categories/' + id)
      .map(
      res => <Usuario>res.json()

      );
  }

  create(usuario: Usuario) {
    return this.http.post('~product/categories', usuario);


  }
  update(usuario: Usuario) {
    return this.http.put('~product/categories/' + usuario.id, usuario);
  }

  delete(usuario: Usuario) {
    return this.http.delete('~product/categories/' + usuario.id);
  }
}
