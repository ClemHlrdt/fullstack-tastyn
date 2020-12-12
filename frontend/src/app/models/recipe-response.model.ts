import { Ingredient } from './ingredient.model';

export interface RecipeResponse {
  id: number;
  name: string;
  duration: number;
  preparation: string;
  difficulty: number;
  imagePath: string;
  pricing: number;
  createdAt: string;
  ingredient: Ingredient[];
  user: {
    id: number;
    username: string;
    email: string;
  };
}
