import { AfterContentInit, Component, ViewContainerRef } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Ng2BootstrapConfig, Ng2BootstrapTheme, ComponentsHelper } from 'ng2-bootstrap/ng2-bootstrap';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
import { AuthService } from '@demoiselle/security';


let w:any = window;

if (w && w.__theme === 'bs4') {
  Ng2BootstrapConfig.theme = Ng2BootstrapTheme.BS4;
}

// global style
import '../style/global.scss';

// the app.component template is the layout itself
let template = require('./layout/layout.template.html');


@Component({
  selector: 'my-app', // <my-app></my-app>
  styleUrls: ['./app.component.scss'],
  template: template
})
export class AppComponent implements AfterContentInit {
  public isBs3:boolean = Ng2BootstrapConfig.theme === Ng2BootstrapTheme.BS3;
  private viewContainerRef:ViewContainerRef;

  public constructor(
    viewContainerRef:ViewContainerRef,
    componentsHelper:ComponentsHelper, 
    private router:Router, 
    private authService: AuthService,
    public toastr: ToastsManager) {
      // You need this small hack in order to catch application root view container ref (ng2-bootstrap)
      this.viewContainerRef = viewContainerRef;
      componentsHelper.setRootViewContainerRef(viewContainerRef);
      this.toastr.setRootViewContainerRef(viewContainerRef); // hack for angular >= 2.2
  }

  public ngAfterContentInit():any {
    // this.router.events.subscribe((event:any) => {
    //   if (event instanceof NavigationEnd) {
    //     if (typeof PR !== 'undefined') {
    //       // google code-prettify
    //       setTimeout(PR.prettyPrint, 50);
    //     }
    //   }
    // });

    // uncomment if the application need to retoken
    //this.authService.initializeReTokenPolling();
  }
}