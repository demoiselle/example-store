

import { Injectable } from '@angular/core';
import { Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Produto } from './produto.model';

@Injectable()
export class ProdutoService {
  
    constructor(private http: Http) {

  }
  list(currentPage: number, itemsPerPage: number) {

    let start = itemsPerPage * (currentPage - 1);
    return this.http.get('~product/products').map(
        res => res.json()
      );
  }

  get(id: number) {
    return this.http.get('~product/products/' + id).map(
        res => <Produto>res.json()
      );
  }

  create(produto: Produto) {
    return this.http.post('~product/products', produto);
  }
  
  update(produto: Produto) {
    return this.http.put('~product/products/' + produto.id, produto);
  }

  delete(produto: Produto) {
    return this.http.delete('~product/products/' + produto.id);
  }
}
