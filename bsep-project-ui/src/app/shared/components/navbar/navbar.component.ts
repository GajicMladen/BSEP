import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Role } from '../../models/Role';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  constructor(private userService: LoginService, private router: Router) {}

  ngOnInit(): void {}

  get isLogged(): boolean {
    return this.userService.isTokenPresent;
  }

  get userRole(): Role | null {
    if (this.userService.user) return this.userService.user.roles[0];
    else return null;
  }

  get Role(): typeof Role {
    return Role;
  }

  logout() {
    this.userService.logout();
    this.router.navigateByUrl('/login');
  }
}
