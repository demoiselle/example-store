/*
 * Correção para funcionar dentro de forms:
 * http://stackoverflow.com/questions/40046955/model-driven-form-with-ng2-bootstrap-datepicker
 * 
 * Alteração para mostrar e esconder:
 * http://stackoverflow.com/questions/36328999/how-to-use-datepicker-popup-in-angular-2
 */

import { Component, Input, Output, EventEmitter, forwardRef, Provider } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

let DATEPICKER_VALUE_ACCESSOR: Provider = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => CustomDatepickerComponent),
  multi: true
};

@Component({
  selector: 'custom-datepicker[formControlName]',
  template: `
    <div class="form-group">
      <label for="dpInput">{{label}}</label>
      <div class="input-group">
        <input id="dpInput" [ngModel]="dateModel | date: 'dd/MM/yyyy'" class="form-control"/>

        <datepicker *ngIf="showDatepicker"
          class="popup form"
          [(ngModel)]="datePickerValue"
          [showWeeks]="true"
          (ngModelChange)="update($event)"
          (click)="hidePopup($event)" >
        </datepicker>

        <span class="input-group-btn">
          <button class=" btn btn-default" type="button" (click)="showPopup()">
            <span class="glyphicon glyphicon-calendar"></span>
          </button>
        </span>
      </div>
    </div>
  `,
  styles: [`
    .popup {
      z-index: 10;
    }
    .form {
      position: absolute;
      background-color: #fff;
      border-radius: 3px;
      border: 1px solid #ddd;
      height: 251px;
    }
    .btn42 {
      height: 30px;
      width: 30px;
    }
  `],  
  providers: [DATEPICKER_VALUE_ACCESSOR]
})
export class CustomDatepickerComponent implements ControlValueAccessor {
  @Input()
  dateModel: Date;
  @Input()
  label: string;
  @Output()
  dateModelChange: EventEmitter<string> = new EventEmitter<string>();
  private showDatepicker: boolean = false;
    
  change = (_: any) => {};
  _customDatePickerValue;
  _datePickerValue: Date;

  get customDatePickerValue() {
    return this._customDatePickerValue;
  }
  set customDatePickerValue(value) {
    this._customDatePickerValue = value;
    this.change(value);
  }

  get datePickerValue() {
    return this._datePickerValue;
  }
  set datePickerValue(value) {
    this._datePickerValue = value;
  }

  writeValue() { }
  registerOnChange(fn) {
    this.change = fn;
  }
  registerOnTouched() { }

  showPopup() {
    this.showDatepicker = true;
    return false;     
  }

  update(event) {
    this.dateModel = event;
    this.dateModelChange.emit(event);
    return false;
  }

  hidePopup(event) {
    // TODO: remover esse workarround e construir uma maneira
    // mais universal de esconder o datepicker 
    if ((event && event.target && event.target.outerText && !isNaN(event.target.outerText))
        ||
        (event && event.target && event.target.firstElementChild &&
         event.target.firstElementChild.innerText && !isNaN(event.target.firstElementChild.innerText)))
    {
      this.showDatepicker = false;
    }
    return false;
  }
}
