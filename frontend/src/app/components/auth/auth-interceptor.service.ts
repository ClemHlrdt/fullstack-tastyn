import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpParams,
} from '@angular/common/http';
import { Observable } from 'rxjs';

import { take, exhaustMap } from 'rxjs/operators';
import { AuthService } from '../../services/auth/auth.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return this.authService.auth.pipe(
      take(1),
      exhaustMap((auth) => {
        if (!auth) {
          return next.handle(req);
        }
        const modifyReq = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${auth.token}`),
        });

        return next.handle(modifyReq);
      })
    );
  }
}
