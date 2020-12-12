import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'difficulty',
})
export class DifficultyPipe implements PipeTransform {
  transform(difficultyValue: number): string {
    let difficulty = '';
    switch (difficultyValue) {
      case 1:
        difficulty = 'Beginner';
        break;
      case 2:
        difficulty = 'Easy';
        break;
      case 3:
        difficulty = 'Intermediate';
        break;
      case 4:
        difficulty = 'Advanced';
        break;
      case 5:
        difficulty = 'Expert';
        break;
    }
    return difficulty;
  }
}
