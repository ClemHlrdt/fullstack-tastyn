import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';
import { Filter } from '../models/filter.model';
import { RecipePage } from '../models/recipe-page.model';
import { RecipeResponse } from '../models/recipe-response.model';
import { Recipe } from '../models/recipe.model';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class DataStorageService {
  constructor(private http: HttpClient) {}

  getUserById(id: string) {
    return this.http.get(`/api/users/${id}`);
  }

  updateUser(id: string, user: User) {
    return this.http.patch<User>(`/api/users/${id}`, user);
  }

  deleteUser(user: User) {
    return this.http.delete(`/api/users/${user.id}`);
  }

  storeRecipes(recipe: Recipe) {
    return this.http.post<RecipeResponse>(`/api/recipes`, recipe);
  }

  getAllRecipes(page: number = 0, filter?: Filter): Observable<RecipePage> {
    let customParams = this.applyFilter(filter);

    return this.http.get<RecipePage>(`/api/recipes?page=${page}`, {
      params: customParams,
    });
  }

  getRecipeById(id: number): Observable<Recipe> {
    return this.http.get<Recipe>(`/api/recipes/${id}`);
  }

  deleteRecipeById(id: number): Observable<Recipe> {
    return this.http.delete<Recipe>(`/api/recipes/${id}`);
  }

  getAllRecipeByUserId(
    userId: string,
    page: number = 0,
    filter?: Filter
  ): Observable<RecipePage> {
    let customParams = this.applyFilter(filter);

    return this.http.get<RecipePage>(
      `/api/recipes/users/${userId}?page=${page}`,
      {
        params: customParams,
      }
    );
  }

  updateRecipeById(id: number, recipe: Recipe) {
    return this.http.patch<Recipe>(`/api/recipes/${id}`, recipe);
  }

  addComment(id: number, comment: Comment) {
    return this.http.post<Comment>(`/api/recipes/${id}/comment`, comment);
  }

  updateComment(recipeId: number, comment: Comment) {
    return this.http.put<Comment>(
      `/api/recipes/${recipeId}/comment/${comment.id}`,
      comment
    );
  }

  removeComment(recipeId: number, commentId: number) {
    return this.http.delete<Comment>(
      `/api/recipes/${recipeId}/comment/${commentId}`
    );
  }

  voteForRecipe(value: number, recipeId: number) {
    return this.http.post<Recipe>(`/api/likes`, {
      value: value,
      recipeId: recipeId,
    });
  }

  getVoteUserVoteForRecipe(recipeId: number) {
    return this.http.get(`/api/likes/${recipeId}`);
  }

  applyFilter(filter: Filter): HttpParams {
    if (filter) {
      let customParams = new HttpParams();
      const query = filter.query != null ? filter.query : '';
      const sort = filter.sort != null ? filter.sort : 'id';
      const order = filter.order != null ? filter.order : 'asc';
      customParams = customParams.append('query', query);
      customParams = customParams.append('sort', sort);
      customParams = customParams.append('order', order);
      return customParams;
    }
    return null;
  }
}
