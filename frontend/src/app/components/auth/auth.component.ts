import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormControl, FormGroup,

  Validators
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';


@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent implements OnInit, OnDestroy {
  isLoaded: boolean = false;
  isRegistration: boolean = false;
  error: any = null;
  private closeSub: Subscription;

  form: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.maxLength(100),
    ]),
  });

  handleChangeMode() {
    this.isLoaded = false;
    this.isRegistration = !this.isRegistration;
    if (this.isRegistration) {
      this.form.addControl('username', new FormControl('', [
        Validators.required,
        Validators.maxLength(15),
      ]))
    } else {
      this.form.removeControl('username')
    }
    this.isLoaded = true;
  }

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
  ) { }

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

    if (this.form.valid && this.isRegistration) {
      const { username, email, password } = this.form.value;
      this.authService.signup(username, email, password);
    }

    if (this.form.valid && !this.isRegistration) {
      const { email, password } = this.form.value;
      this.authService.login(email, password);
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
