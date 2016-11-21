import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

import { Ng2BootstrapConfig, Ng2BootstrapTheme } from 'ng2-bootstrap/components/ng2-bootstrap-config';
import { routes } from './../../app.routing';

// webpack html imports
let template = require('./sidebar-menu.template.html');

@Component({
  selector: 'sidebar-menu',
  template
})

export class SidebarMenuComponent {
  public isBs3:boolean = Ng2BootstrapConfig.theme === Ng2BootstrapTheme.BS3;
  public routes:any = routes;
  public search:any = {};
  public hash:string = '';

  public constructor(private router:Router) {
    this.routes = this.routes.filter(
      (v:any) => v.path !== '**' && v.data
      
    );
    this.router.events.subscribe((event:any) => {
      if (event instanceof NavigationEnd) {
        this.hash = event.url;
      }
    });
  }
}
