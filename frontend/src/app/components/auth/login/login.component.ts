import { Component, OnInit, OnDestroy } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, OnDestroy {
  isLoaded: boolean = false;
  isEmailValid: boolean = true;
  error: any = null;

  private closeSub: Subscription;

  form: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.maxLength(100),
      ]),
    });
    this.authService.errorSubject.subscribe((errorMessage) => {
      this.error = errorMessage;
      this.isLoaded = true;
    });
  }

  validateEmail() {
    if (!this.form.get('email').valid) {
      this.isEmailValid = false;
    } else {
      this.isEmailValid = true;
    }
  }

  onSubmit() {
    if (this.form.valid) {
      this.isLoaded = false;
      const { email, password } = this.form.value;

      this.authService.login(email, password);
    }
  }

  ngOnDestroy() {
    if (this.closeSub) {
      this.closeSub.unsubscribe();
    }
  }
}
