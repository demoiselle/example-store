import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@demoiselle/security';

@Component({
  selector: 'dml-usuario-dropdown',
  templateUrl: './usuario-dropdown.template.html'
})
export class UsuarioDropdownComponent {

  public constructor(private router: Router, private authService: AuthService) {
  }

  isLoggedIn() {
    return this.authService.isAuthenticated();
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
