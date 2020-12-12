import { RecipeServingPipe } from './recipe-serving.pipe';

describe('RecipeServingPipe', () => {
  it('create an instance', () => {
    const pipe = new RecipeServingPipe();
    expect(pipe).toBeTruthy();
  });
});
