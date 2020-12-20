import { Component, OnDestroy, OnInit, isDevMode } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { AuthService } from './services/auth/auth.service';

import { routeTransitionAnimations, fadeAnimation } from './route-animations';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [fadeAnimation],
})
export class AppComponent implements OnInit, OnDestroy {
  constructor(private authService: AuthService) {}
  ngOnInit() {
    this.authService.autoLogin();
    this.enableDarkMode();
  }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData;
  }

  ngOnDestroy() {}

  enableDarkMode() {
    // On page load or when changing themes, best to add inline in `head` to avoid FOUC
    if (
      localStorage.theme === 'dark' ||
      (!('theme' in localStorage) &&
        window.matchMedia('(prefers-color-scheme: dark)').matches)
    ) {
      console.log('will be dark');

      document.querySelector('html').classList.add('dark');
    } else {
      console.log('will be light');
      document.querySelector('html').classList.remove('dark');
    }
  }
}
