import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Usuario } from './usuario.model';

@Injectable()
export class ContaService {

  constructor(protected http: Http) { }

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

}