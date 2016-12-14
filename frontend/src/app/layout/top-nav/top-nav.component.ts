import { AfterViewInit, Component, Inject, Renderer } from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';
import { NavigationEnd, Router } from '@angular/router';

import { AuthService } from '@demoiselle/security';

// webpack html imports
let template = require('./top-nav.template.html');

@Component({
  selector: 'top-nav',
  styleUrls: ['./top-nav.component.scss'],
  template
})
export class TopNavComponent implements AfterViewInit {
  public isShown: boolean = false;

  private renderer: Renderer;
  private document: any;

  public constructor(renderer: Renderer, @Inject(DOCUMENT) document: any, private router: Router, private authService: AuthService) {
    this.renderer = renderer;
    this.document = document;
  }

  public ngAfterViewInit(): any {
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        this.toggle(false);
      }
    });
  }

  public toggle(isShown?: boolean): void {
    // let anchorEl = this.document.body;
    let anchorEl = document.getElementById('sidebar-menu');

    this.isShown = typeof isShown === 'undefined' ? !this.isShown : isShown;
    if (this.document && this.document.body) {
      this.renderer.setElementClass(anchorEl, 'isOpenMenu', this.isShown);
      if (this.isShown === false) {
        this.renderer.setElementProperty(anchorEl, 'scrollTop', 0);
      }
    }
  }

  isLoggedIn() {
    return this.authService.isAuthenticated();
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
