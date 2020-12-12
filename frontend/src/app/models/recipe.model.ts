import { Ingredient } from './ingredient.model';
import { User } from './user.model';
import { Comment } from './comment.model';

export class Recipe {
  constructor(
    public name: string,
    public duration: number,
    public preparation: string,
    public difficulty: number | string,
    public serving: number,
    public pricing: number,
    public imagePath: string,
    public votes?: number,
    public ingredients?: Ingredient[],
    public user?: User,
    public id?: number,
    public createdAt?: string,
    public comments?: Comment[]
  ) {}
}
