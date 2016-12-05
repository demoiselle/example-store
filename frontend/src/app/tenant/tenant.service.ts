

import { Injectable, EventEmitter } from '@angular/core';
import { Http} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { ITenant, Tenant } from './tenant.model';

@Injectable()
export class TenantService {
  
    public tenantChanged: EventEmitter<Tenant>;
    public tenantsChanged: EventEmitter<Tenant>;

    private apiUrl: string = process.env.CONF.multitenancy.apiUrl;

    constructor(private http: Http) {
       this.tenantChanged = new EventEmitter<Tenant>();
       this.tenantsChanged = new EventEmitter<Tenant>();
    }
    list () {

        return this.http.get(this.apiUrl + 'multiTenancy')
                    .map(
                      res => <Tenant[]> res.json()
                      
                    );
    }

    create (tenant: Tenant) {
      
      return this.http.post(this.apiUrl + 'multiTenancy/createTenant', tenant)
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
      return this.http.delete(this.apiUrl + 'multiTenancy/deleteTenant/' + tenant.id)
        .map(
          () => {
            this.tenantsChanged.emit(tenant);
          } 
        );
    }

    selectTenant(tenant:Tenant) {
        localStorage.setItem('dml_tenant', JSON.stringify(tenant));
        this.tenantChanged.emit(tenant);
    }

    getSelectedTenant(): Tenant {
      if(localStorage.getItem('dml_tenant')) {
        return JSON.parse(localStorage.getItem('dml_tenant'));
      } else {
        return new Tenant(0, 'No Tenant');
      }
      
    }
}
