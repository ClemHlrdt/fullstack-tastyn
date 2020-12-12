import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Authentication } from 'src/app/models/authentication.model';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  isOpen: boolean = false;
  isAuthenticated: boolean = false;
  auth: Authentication;
  isLoaded: boolean = false;

  private subscriptions: Subscription[] = [];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    let authSub = this.authService.auth.subscribe((auth: Authentication) => {
      this.isAuthenticated = !!auth;
      if (auth) {
        this.auth = auth;
        this.isLoaded = true;
      }
    });

    this.subscriptions.push(authSub);
  }

  onOpen() {
    this.isOpen = !this.isOpen;
  }

  navigateToUserSection() {
    this.isAuthenticated
      ? this.router.navigate(['users', this.auth.userId, 'recipes'])
      : null;
  }

  isActive(url): boolean {
    return this.router.url.includes(url);
  }

  onLogout() {
    this.authService.logout();
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
