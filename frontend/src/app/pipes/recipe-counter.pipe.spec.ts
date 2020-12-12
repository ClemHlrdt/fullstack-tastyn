import { RecipeCounterPipe } from './recipe-counter.pipe';

describe('RecipeCounterPipe', () => {
  it('create an instance', () => {
    const pipe = new RecipeCounterPipe();
    expect(pipe).toBeTruthy();
  });
});
