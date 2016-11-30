import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'amount-spinner',
  template: `
    <button (click)="decrement()">-</button>
    <input type="text" [(ngModel)]="amount">
    <button (click)="increment()">+</button>
  `,
  styles: [`
    input {
      position: relative;
      white-space: nowrap;
      width: 30px;
      vertical-align: middle;
      display: table-cell;
      text-align: right;
    }
  `]
})
export class AmountSpinnerComponent {
  
  amountValue = 0;
  @Output() amountChange = new EventEmitter();
  
  @Input()
  get amount() {
    return this.amountValue;
  }
  
  set amount(val) {
    this.amountValue = val;
    this.amountChange.emit(this.amountValue);
  }
  
  decrement() {
    if (this.amount > 1) {
      this.amount--;
    }
  }
  
  increment() {
    this.amount++;
  }
}