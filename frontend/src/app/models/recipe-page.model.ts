import { Recipe } from './recipe.model';

export class RecipePage {
  constructor(
    public totalRecipes: number,
    public totalPages: number,
    public recipes: Recipe[]
  ) {}
}
