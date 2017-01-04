import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@demoiselle/security';
import { ContaService } from './conta.service';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

@Component({
    selector: 'dml-conta',
    templateUrl: './conta.component.html'
})
export class ContaComponent implements OnInit {

    protected usuario;

    constructor(protected router: Router,
        protected authService: AuthService,
        protected contaService: ContaService,
        protected toastr: ToastsManager) { }

    ngOnInit() {
        console.log('CONTA ON INIT');
    }

    editarUsuario() {
        let data = this.authService.getDataFromToken();
        console.log('TOKEN DATA');
        console.log(data);
        if (data) {
            console.log(data.params.email);
            this.contaService.getUserByEmail(data.params.email).subscribe( 
                (result) => {
                    console.log('RESULT');
                    console.log(result);
                    this.usuario = result;
                    this.router.navigate(['/usuario/edit/'+this.usuario.id]);
                },
                (error) => {
                    this.usuario = null;
                    console.log('ERROR');
                    console.log(error);
                    this.toastr.error('Não foi possível carregar o usuário.');
                }
            );
        } else {
            this.toastr.error('Nenhum token encontrado.');
        }
    }

}