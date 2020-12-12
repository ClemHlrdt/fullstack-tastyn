import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { AuthGuard } from '../router/auth.guard';
import { RecipeListComponent } from '../components/recipes/recipe-list/recipe-list.component';
import { AddRecipeComponent } from '../components/recipes/add-recipe/add-recipe.component';
import { RecipeDetailsComponent } from '../components/recipes/details/recipe-details.component';
import { RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    canActivate: [AuthGuard],
    //component: RecipeListComponent,
    children: [
      {
        path: '',
        component: RecipeListComponent,
      },
      { path: 'add', component: AddRecipeComponent },
      {
        path: ':id',
        component: RecipeDetailsComponent,
      },
      { path: ':id/update', component: AddRecipeComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RecipesRoutingModule {}
