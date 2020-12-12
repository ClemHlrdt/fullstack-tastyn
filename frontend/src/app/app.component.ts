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
    if (isDevMode()) {
      console.log('Development!');
    } else {
      console.log('Cool. Production!');
    }
  }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData;
  }

  ngOnDestroy() {}
}
