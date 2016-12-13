import { Injectable, EventEmitter, Output } from '@angular/core';
import { Http} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Tenant } from './tenant.model';
import { Usuario } from '../usuario/usuario.model';

@Injectable()
export class TenantService {
  
    public tenantChanged: EventEmitter<Tenant>;
    public tenantsChanged: EventEmitter<Tenant>;
    public tenantCreated: EventEmitter<boolean>;

    protected apiUrl: string = process.env.CONF.multitenancy.apiUrl;

    protected saleUrl: string = process.env.CONF.endpoints.sale;
    protected productUrl: string = process.env.CONF.endpoints.product;
    protected userUrl: string = process.env.CONF.endpoints.user;

    constructor(protected http: Http) {
       this.tenantChanged = new EventEmitter<Tenant>();
       this.tenantsChanged = new EventEmitter<Tenant>();
       this.tenantCreated = new EventEmitter<boolean>();
    }
    list () {

        return this.http.get(this.apiUrl + 'tenants')
                    .map(
                      res => <Tenant[]> res.json()
                      
                    );
    }

    create (tenant: Tenant, user: Usuario) {
      Observable.forkJoin(
        this.http.post(this.saleUrl + 'tenants', tenant)
          .map(() => {}),
        this.http.post(this.productUrl + 'tenants', tenant)
          .map(() => {}),
        this.http.post(this.userUrl + 'tenants', tenant)
          .map(() => { this.tenantsChanged.emit(tenant); })
      ).subscribe(
        () => {
          this.createUserOnTenant(encodeURI(tenant.name), user).subscribe(
            () => {
              console.log('NOT ERROR');
              this.tenantCreated.emit(true);
            },
            (error) => {
              console.log('ERROR');
              console.log(error);
              this.rollbackTenant(tenant);
              this.tenantCreated.emit(false);
            }
          );
        },
        (error) => {
          console.log('ERROR');
          console.log(error);
          this.rollbackTenant(tenant);
          this.tenantCreated.emit(false);
        }
      );
    }

    createUserOnTenant(tenant: string, user: Usuario) {
      return this.http.post(this.userUrl+tenant+'/users', user);
    }

    rollbackTenant(tenant: Tenant) {
    }

    update (tenant: Tenant) {
        return this.http.put(this.apiUrl + 'tenants', tenant)
                    .map(
                      res => <Tenant> res.json()
                      
                    );
    }

    delete(tenant: Tenant){
      return this.http.delete(this.apiUrl + 'tenants/' + tenant.id)
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
