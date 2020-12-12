import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { RecipePage } from 'src/app/models/recipe-page.model';
import { DataStorageService } from 'src/app/services/data-storage.service';
import { Filter } from '../../models/filter.model';

@Injectable({
  providedIn: 'root',
})
export class RecipesService {
  filter: Filter = null;

  private _recipes: Subject<RecipePage> = new Subject<RecipePage>();

  constructor(private dataStorageService: DataStorageService) {}

  get recipes() {
    return this._recipes.asObservable();
  }

  loadAllRecipes(page: number) {
    this.dataStorageService
      .getAllRecipes(page, this.filter)
      .subscribe((recipePage: RecipePage) => {
        this._recipes.next(recipePage);
      });
  }

  loadAllRecipesForUser(page: number, userId: string) {
    this.dataStorageService
      .getAllRecipeByUserId(userId, page, this.filter)
      .subscribe((recipePage: RecipePage) => {
        this._recipes.next(recipePage);
      });
  }

  setFilter(filter: Filter) {
    this.filter = filter;
  }
}
