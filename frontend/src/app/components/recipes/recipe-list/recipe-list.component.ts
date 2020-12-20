import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RecipePage } from 'src/app/models/recipe-page.model';
import { Recipe } from 'src/app/models/recipe.model';
import { RecipesService } from 'src/app/services/recipes/recipes.service';

import {
  trigger,
  transition,
  style,
  animate,
  query,
  stagger,
} from '@angular/animations';
import { DarkModeService } from 'src/app/services/dark-mode/dark-mode.service';

const listAnimation = trigger('listAnimation', [
  transition('* <=> *', [
    query(
      ':enter',
      [
        style({ opacity: 0 }),
        stagger('200ms', animate('600ms ease-in-out', style({ opacity: 1 }))),
      ],
      { optional: true }
    ),
    query(':leave', animate('200ms', style({ opacity: 0 })), {
      optional: true,
    }),
  ]),
]);

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
  animations: [listAnimation],
})
export class RecipeListComponent implements OnInit, OnDestroy {
  recipes: Recipe[];

  currentPage: number = 1;
  pageSize: number = 20;
  total: number;
  isLoaded: boolean = false;
  isDark: boolean = false;

  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private recipesService: RecipesService,
    private darkModeService: DarkModeService
  ) {}

  ngOnInit(): void {
    this.recipesService.setFilter(null);
    this.loadRecipes(this.currentPage);
    this.isDark = this.darkModeService.isDarkMode;
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
