// This shows a different way of testing a component, check about for a simpler one
import { Component } from '@angular/core';

import { TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';

import {SecurityModule} from '@demoiselle/security';
import {HttpModule} from '@angular/http';


describe('Home Component', () => {
  const html = '<dml-home></dml-home>';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SecurityModule, HttpModule],
      declarations: [HomeComponent, TestComponent]
    });
    TestBed.overrideComponent(TestComponent, { set: { template: html }});
  });

  it('should have text home', () => {
    const fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
    expect(fixture.nativeElement.children[0].textContent).toContain('Home');
  });

});

@Component({selector: 'my-test', template: ''})
class TestComponent { }
