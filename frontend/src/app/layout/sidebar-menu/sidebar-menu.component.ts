import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { routes } from './../../app.routing';
import { AuthService } from '@demoiselle/security';

// webpack html imports
let template = require('./sidebar-menu.template.html');

@Component({
  selector: 'sidebar-menu',
  template
})

export class SidebarMenuComponent {
  public routes:any = routes;
  public search:any = {};
  public hash:string = '';

  public constructor(private router:Router, private authService: AuthService) {
    this.routes = this.routes.filter(
      (v:any) => v.path !== '**' && v.data
      
    );
    this.router.events.subscribe((event:any) => {
      if (event instanceof NavigationEnd) {
        this.hash = event.url;
      }
    });

    
    //console.log(this.authService.isAuthorized(['ADMINISTRATOR']));

  }
}
