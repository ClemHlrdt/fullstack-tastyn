import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Authentication } from 'src/app/models/authentication.model';
import { AuthResponseData } from 'src/app/models/AuthResponseData.model';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _authSubject: BehaviorSubject<Authentication> = new BehaviorSubject<
    Authentication
  >(null);

  errorSubject: any = new BehaviorSubject<any>(null);
  errorMessage: any = this.errorSubject.asObservable();
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  get auth(): Observable<Authentication> {
    return this._authSubject;
  }

  signup(username: string, email: string, password: string) {
    return this.http
      .post<AuthResponseData>(`/api/auth/signup`, {
        username: username,
        email: email,
        password: password,
      })
      .subscribe(
        (data) => {
          if (data && data.accessToken) {
            this.handleAuthentication(data.accessToken);
            this.router.navigateByUrl('recipes');
          }
        },
        (error) => {
          this.errorSubject.next(error.error.message);
        }
      );
  }

  login(email: string, password: string) {
    this.http
      .post<AuthResponseData>(`/api/auth/signin`, {
        email,
        password,
      })
      .subscribe(
        (data) => {
          if (data && data.accessToken) {
            this.handleAuthentication(data.accessToken);
            this.router.navigateByUrl('recipes');
          }
        },
        (error) => {
          this.errorSubject.next(error.error.message);
        }
      );
  }

  private handleAuthentication(token: string) {
    // Decode Jwt Token
    const body: {
      exp: number;
      iat: number;
      sub: string;
      userId: string;
    } = this.decodeJwt(token);

    const expirationDate = new Date(body.exp * 1000);

    const auth = new Authentication(
      body.exp,
      body.iat,
      body.sub,
      body.userId,
      token
    );

    this._authSubject.next(auth);
    this.autoLogout(expirationDate.getTime());

    localStorage.setItem('authData', JSON.stringify(auth));
  }

  autoLogin() {
    // get data from localStorage or return
    const authData: Authentication = JSON.parse(
      localStorage.getItem('authData')
    );
    if (!authData) {
      return;
    }

    // Create a new Auth
    const loadedAuth = new Authentication(
      authData.expiration,
      authData.issuedAt,
      authData.email,
      authData.userId,
      authData.token
    );

    if (loadedAuth) {
      this._authSubject.next(loadedAuth);

      const expirationDuration =
        authData.expiration * 1000 - new Date().getTime();

      this.autoLogout(expirationDuration);
    }
  }

  autoLogout(expirationDate: number) {
    if (expirationDate < 0) {
      this.logout();
    }

    this.tokenExpirationTimer = this.logoutTimeout(() => {
      this.logout();
    }, expirationDate);
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorObject: { errorMessage: string; errorDetails: string[] };
    let errorMessage = 'An unknown error occurred!';
    let details: String[] = [];
    if (!errorRes.error) {
      return throwError(errorMessage);
    }

    switch (errorRes.error.message) {
      case 'USERNAME_TAKEN':
        errorMessage = 'This username is already taken';
      case 'EMAIL_TAKEN':
        errorMessage = 'This email exists already!';
        break;
      case 'INVALID_FORM':
        errorMessage = 'Invalid form';
        details = errorRes.error.details.map((x) => x.messageError);
        break;
      case 'UNAUTHORIZED':
        errorMessage = 'Invalid credentials';
        break;
      case 'BAD_CREDENTIALS':
        errorMessage = 'Email and/or password are invalid';
        break;
    }

    return throwError({ errorMessage: errorMessage, errorDetails: details });
  }

  logout() {
    this._authSubject.next(null);
    this.router.navigate(['/']);
    localStorage.removeItem('authData');

    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  private decodeJwt(token) {
    const body = token.split('.')[1];
    return JSON.parse(atob(body));
  }

  logoutTimeout(fn, delay) {
    var maxDelay = Math.pow(2, 31) - 1;

    if (delay > maxDelay) {
      var args = arguments;
      args[1] -= maxDelay;

      return setTimeout(function () {
        this.logoutTimeout.apply(undefined, args);
      }, maxDelay);
    }

    return setTimeout.apply(undefined, arguments);
  }
}
