import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Authentication } from 'src/app/models/authentication.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { BreakpointObserver, BreakpointState } from '@angular/cdk/layout';

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
  isFullSize: boolean = false;

  private subscriptions: Subscription[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private breakpointObserver: BreakpointObserver
  ) {}

  ngOnInit(): void {
    this.breakpointObserver
      .observe(['(min-width: 1024px)'])
      .subscribe((state: BreakpointState) => {
        if (state.matches) {
          this.isOpen = true;
        } else {
          this.isOpen = false;
        }
      });

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
