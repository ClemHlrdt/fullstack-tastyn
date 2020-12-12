import { Pipe, PipeTransform } from '@angular/core';
import { Ingredient } from 'src/app/models/ingredient.model';

@Pipe({
  name: 'recipeCounter',
})
export class RecipeCounterPipe implements PipeTransform {
  transform(ings: Ingredient[]): string {
    const ingredients = ings.length;
    if (ingredients > 1) {
      return `${ingredients} ingredients`;
    } else if (ingredients == 1) {
      return '1 ingredient';
    } else {
      return 'not mentioned';
    }
  }
}
