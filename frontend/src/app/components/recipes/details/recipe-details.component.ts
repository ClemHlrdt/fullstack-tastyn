import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable, Subscription, zip } from 'rxjs';
import { Authentication } from 'src/app/models/authentication.model';
import { Recipe } from 'src/app/models/recipe.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { RecipeService } from 'src/app/services/recipe/recipe.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.scss'],
})
export class RecipeDetailsComponent implements OnInit, OnDestroy {
  recipe: Recipe;
  id: number;
  auth: Authentication;

  private subscriptions: Subscription[] = [];

  comments: Comment[];

  error: null;
  isLoaded: boolean = false;
  isOwner: boolean;
  defaultImage = '/assets/img/recipe-placeholder.jpg';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService,
    private recipeService: RecipeService
  ) {}

  ngOnInit(): void {
    const routeSub = this.route.params.subscribe((params: Params) => {
      this.id = +params.id;

      const recipeSub: Observable<Recipe> = this.recipeService.recipe;
      const authSub: Observable<Authentication> = this.authService.auth;

      const sub = zip(recipeSub, authSub).subscribe((data) => {
        this.recipe = data[0];
        this.auth = data[1];
        if (this.isSameUser(this.auth)) this.isOwner = true;
        this.isLoaded = true;
      });

      this.recipeService.getRecipeById(this.id);
      this.subscriptions.push(sub);
    });
    this.subscriptions.push(routeSub);
  }

  onUpdate() {
    this.router.navigate(['/recipes', this.id, 'update']);
  }

  onDelete() {
    const recipeSub = this.recipeService
      .deleteRecipeById(this.id)
      .subscribe(() => {
        this.toastr.info('Recipe successfully removed!');
        this.router.navigate(['/recipes']);
      });
    this.subscriptions.push(recipeSub);
  }

  private isSameUser(auth: Authentication) {
    return auth.userId === this.recipe.user.id;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
