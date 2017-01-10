import { Component, OnInit } from '@angular/core';

@Component({
    selector:'compra',
    providers:[],
    templateUrl: './compra.component.html'
})

export class CompraComponent implements OnInit {

    protected saleResult: Object;
    protected itens: Object[];

    constructor(
    ) { }

    ngOnInit() {
        if (localStorage && localStorage.getItem('sale_complete')) {
            this.saleResult = JSON.parse(localStorage.getItem('sale_complete'));
            this.itens = this.saleResult['itens'];
            localStorage.removeItem('sale_complete');
        }
        console.log(this.saleResult);
    }

}