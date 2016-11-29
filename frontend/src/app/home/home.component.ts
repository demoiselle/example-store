import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'dml-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public news= 'Demoiselle frontend based on Angular 2';
  constructor() {
    // Do stuff
  }

  ngOnInit() {
    console.log('Hello Home');
  }

}
