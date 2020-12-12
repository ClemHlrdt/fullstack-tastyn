import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Recipe } from '../../models/recipe.model';
import { DataStorageService } from '../data-storage.service';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  private _recipe = new Subject<Recipe>();
  private dataStore: Recipe;

  constructor(private dataStorageService: DataStorageService) {}

  public get recipe(): Observable<Recipe> {
    return this._recipe;
  }

  getRecipeById(id: number) {
    this.dataStorageService.getRecipeById(id).subscribe(
      (recipe: Recipe) => {
        this.dataStore = recipe;
        this._recipe.next(Object.assign({}, this.dataStore));
      },
      (error) => console.log("Couldn't load recipe")
    );
  }

  addRecipe(recipe: Recipe) {
    return this.dataStorageService.storeRecipes(recipe);
  }

  updateRecipeById(id: number, recipe: Recipe) {
    return this.dataStorageService.updateRecipeById(id, recipe);
  }

  deleteRecipeById(id: number) {
    return this.dataStorageService.deleteRecipeById(id);
  }
}
