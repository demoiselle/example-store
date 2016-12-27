import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@demoiselle/security';
import { ContaService } from './conta.service';

@Component({
    selector: 'dml-conta',
    templateUrl: './conta.component.html'
})
export class ContaComponent implements OnInit {

    protected usuario;

    constructor(protected router: Router,
        protected authService: AuthService,
        protected contaService: ContaService) { }

    ngOnInit() {
        console.log('CONTA ON INIT');
        let data = this.authService.getDataFromToken();
        if (data) {
            this.contaService.getUserByEmail(data.params.email).subscribe( 
                (result) => {
                    this.usuario = result;
                },
                (error) => {
                    this.usuario = null;
                    console.log(error);
                }
            );
        }
    }

    editarUsuario() {
        this.router.navigate(['/usuario/edit/'+this.usuario.id]);
        //[routerLink]="['/usuario','edit', usuario.id]"
    }

}