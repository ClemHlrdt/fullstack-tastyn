import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Authentication } from 'src/app/models/authentication.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UploadService } from 'src/app/services/upload/upload.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss'],
})
export class UserEditComponent implements OnInit, OnDestroy {
  isLoaded: boolean = false;
  isOwner: boolean = true;

  subscriptions: Subscription[] = [];

  @ViewChild('fileUpload', { static: false })
  fileUpload: ElementRef;
  files = [];

  private avatarUrl: string = '';
  user: User;

  userForm: FormGroup;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private uploadService: UploadService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe((data) => {
      this.user = data.user;

      const auth = this.authService.auth.subscribe((auth: Authentication) => {
        if (auth.userId !== this.user.id) {
          this.router.navigate(['/users', this.user.id]);
        } else {
          this.fillForm();
          this.isLoaded = true;
        }
      });

      this.subscriptions.push(auth);
    });
  }

  fillForm() {
    this.userForm = this.fb.group({
      username: new FormControl(this.user.username, [Validators.required]),
      email: new FormControl(this.user.email, [
        Validators.required,
        Validators.email,
      ]),
      description: new FormControl(this.user.description),
    });
  }

  uploadFile(file) {
    if (file.data.size > 10000000) {
      throw 'File too large';
    }

    const formData = new FormData();
    formData.append('file', file.data);

    this.isLoaded = false;

    this.uploadService.upload(this.user, formData).subscribe((user: User) => {
      this.userService.userSubject.next(user);
      this.user = user;
      this.isLoaded = true;
    });
  }

  private uploadFiles() {
    this.uploadFile(this.files[0]);
  }

  onClick() {
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      const file = fileUpload.files[0];
      this.files.push({ data: file });

      this.uploadFiles();
    };
    fileUpload.click();
    this.files = [];
  }

  onSubmit() {
    this.isLoaded = false;
    const { username, email, description } = this.userForm.value;
    const updatedUser = new User(
      username,
      email,
      this.user.id,
      null,
      null,
      null,
      null,
      description
    );
    this.userService.updateUser(this.user, updatedUser).subscribe(
      (data) => {
        this.router.navigate(['/users', this.user.id]);
      },
      (err) => console.log(err)
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
