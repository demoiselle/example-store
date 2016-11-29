import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'amount-spinner',
  template: `
    <button (click)="decrement()">-</button>
    <span>{{amount}}</span>
    <button (click)="increment()">+</button>
  `
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
    this.amount--;
  }
  
  increment() {
    this.amount++;
  }
}