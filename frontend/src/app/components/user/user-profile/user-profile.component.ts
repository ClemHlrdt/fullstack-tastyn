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
import { RecipePage } from 'src/app/models/recipe-page.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { RecipesService } from 'src/app/services/recipes/recipes.service';
import { UploadService } from 'src/app/services/upload/upload.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit, OnDestroy {
  constructor(
    private authService: AuthService,
    private userService: UserService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private recipesService: RecipesService,
    private uploadService: UploadService
  ) {}

  @ViewChild('fileUpload', { static: false })
  fileUpload: ElementRef;
  files = [];

  subscriptions: Subscription[] = [];

  user: User;
  auth: Authentication;

  isLoaded: boolean = false;
  isOwner: boolean = false;

  loggedInUser: User = null;
  recipeCount: number = 0;

  userForm: FormGroup;

  // User avatar properties
  selectedFile: File;
  hasImage: boolean = false;

  ngOnInit(): void {
    this.route.data.subscribe((data) => {
      this.user = data.user;

      this.recipesService.loadAllRecipesForUser(0, this.user.id);
      this.loadUserRecipes();
      this.isOwnerOfRecipes();
    });
  }

  loadUser() {
    this.route.data.subscribe((data) => {
      this.user = data.user;
    });
  }

  loadUserRecipes() {
    const recipeSub = this.recipesService.recipes.subscribe(
      (recipePage: RecipePage) => {
        this.recipeCount = recipePage.totalRecipes;
      }
    );
    this.subscriptions.push(recipeSub);
  }

  isOwnerOfRecipes() {
    const auth = this.authService.auth.subscribe((auth: Authentication) => {
      if (auth && auth.userId === this.user.id) {
        this.isOwner = true;
      }
    });
    this.subscriptions.push(auth);
    this.isLoaded = true;
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
  }

  private fillUserForm() {
    if (!this.isOwner) {
      this.userForm = this.fb.group({
        username: new FormControl('', Validators.required),
        email: new FormControl('', Validators.required),
      });
    } else {
      this.userForm = this.fb.group({
        username: new FormControl(this.user.username, Validators.required),
        email: new FormControl(this.user.email, Validators.required),
        description: new FormControl(this.user.description),
      });
    }
  }

  onSubmit() {
    const { username, email, description } = this.userForm.value;

    const user = new User(
      username,
      email,
      this.loggedInUser.id,
      null,
      null,
      null,
      null,
      description
    );

    const userSub = this.userService
      .updateUser(this.loggedInUser, user)
      .subscribe((user: User) => {
        this.router.navigate(['profile', user.username]);
      });
    this.subscriptions.push(userSub);
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
