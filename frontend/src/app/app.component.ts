import { AfterContentInit, Component, ViewContainerRef } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Ng2BootstrapConfig, Ng2BootstrapTheme, ComponentsHelper } from 'ng2-bootstrap/ng2-bootstrap';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
import { AuthService } from '@demoiselle/security';

// global style
import '../style/global.scss';

@Component({
  selector: 'my-app', // <my-app></my-app>
  styleUrls: ['./app.component.scss'],
  template: require('./app.component.html')
})
export class AppComponent implements AfterContentInit {
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
    
    // uncomment if the application need to retoken
    //this.authService.initializeReTokenPolling();
  }
}