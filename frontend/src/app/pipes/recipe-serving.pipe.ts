import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'recipeServing',
})
export class RecipeServingPipe implements PipeTransform {
  transform(servings: number): string {
    return servings > 1 ? `${servings} servings` : `${servings} serving`;
  }
}
