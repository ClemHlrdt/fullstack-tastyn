import { Component, OnInit, OnDestroy } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/services/auth/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit, OnDestroy {
  isLoaded: boolean = false;
  error: any = null;
  private closeSub: Subscription;

  form = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.maxLength(15),
    ]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.maxLength(100),
    ]),
  });

  get username() {
    return this.form.get('username');
  }
  get email() {
    return this.form.get('email');
  }
  get password() {
    return this.form.get('password');
  }

  constructor(
    private authService: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.authService.errorSubject.subscribe((errorMessage) => {
      this.error = errorMessage;
      this.isLoaded = true;
    });
  }

  onSubmit() {
    if (!this.form.valid) {
      return;
    }
    this.isLoaded = false;

    if (this.form.valid) {
      const { username, email, password } = this.form.value;
      this.authService.signup(username, email, password);
    }

    this.form.reset();
  }

  onHandleError() {
    this.error = null;
  }

  private showErrorAlert(message: string, details: string[] = null) {
    this.toastr.error('Error', message);
  }

  ngOnDestroy() {
    if (this.closeSub) {
      this.closeSub.unsubscribe();
    }
  }
}
