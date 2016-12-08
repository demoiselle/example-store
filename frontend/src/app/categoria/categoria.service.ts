

import { Injectable } from '@angular/core';
import { Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Categoria } from './categoria.model';

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
      res => <Categoria>res.json()

      );
  }

  create(categoria: Categoria) {
    return this.http.post('~product/categories', categoria);


  }
  update(categoria: Categoria) {
    return this.http.put('~product/categories/' + categoria.id, categoria);
  }

  delete(categoria: Categoria) {
    return this.http.delete('~product/categories/' + categoria.id);
  }
}
