import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipePage } from 'src/app/models/recipe-page.model';
import { Recipe } from 'src/app/models/recipe.model';
import { RecipesService } from 'src/app/services/recipes/recipes.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit, OnDestroy {
  recipes: Recipe[];

  currentPage: number = 1;
  pageSize: number = 20;
  total: number;
  isLoaded: boolean = false;

  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private recipesService: RecipesService
  ) {}

  ngOnInit(): void {
    this.recipesService.setFilter(null);
    this.loadRecipes(this.currentPage);
  }

  loadRecipes(page) {
    this.currentPage = page;
    if (this.router.url === '/recipes') {
      this.isLoaded = false;
      this.recipesService.loadAllRecipes(page - 1);
    } else {
      this.route.params.subscribe((params: Params) => {
        this.isLoaded = false;
        this.recipesService.loadAllRecipesForUser(page - 1, params.id);
      });
    }

    const recipeSub = this.recipesService.recipes.subscribe(
      (recipes: RecipePage) => {
        if (recipes) {
          this.recipes = recipes.recipes;
          this.total = recipes.totalRecipes;
          this.isLoaded = true;
        }
      }
    );
    this.subscriptions.push(recipeSub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
