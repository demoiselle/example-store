

import { Injectable, EventEmitter } from '@angular/core';
import { Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { ITenant, Tenant } from './tenant.model';

@Injectable()
export class TenantService {
  
    public tenantsChanged: EventEmitter<Tenant>;

    constructor(private http: Http) {
       this.tenantsChanged = new EventEmitter<Tenant>();
    }
    list () {

        return this.http.get('~multiTenancy/multiTenancy')
                    .map(
                      res => <Tenant[]> res.json()
                      
                    );
    }

    create (tenant: Tenant) {
      
      return this.http.post('~multiTenancy/multiTenancy/createTenant', tenant)
        .map(
          () => {
            this.tenantsChanged.emit(tenant);
          } 
        );

    }
    // update (tenant: Tenant) {
    //     return this.http.put('~livraria/user', tenant)
    //                 .map(
    //                   res => <Tenant> res.json()
                      
    //                 );
    // }

    delete(tenant: Tenant){
      return this.http.delete('~multiTenancy/multiTenancy/deleteTenant/' + tenant.id)
        .map(
          () => {
            this.tenantsChanged.emit(tenant);
          } 
        );
    }

    selectTenant(tenant:Tenant) {
        localStorage.setItem('dml_tenant', JSON.stringify(tenant));
    }

    getSelectedTenant(): Tenant {
      if(localStorage.getItem('dml_tenant')) {
        return JSON.parse(localStorage.getItem('dml_tenant'));
      } else {
        return new Tenant(0, 'No Tenant');
      }
      
    }
}
