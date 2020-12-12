import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AddRecipeComponent } from '../components/recipes/add-recipe/add-recipe.component';
import { CommentsComponent } from '../components/recipes/comments/comments.component';
import { NewCommentComponent } from '../components/recipes/comments/new-comment/new-comment.component';
import { RecipeDetailsComponent } from '../components/recipes/details/recipe-details.component';
import { RecipeItemComponent } from '../components/recipes/recipe-list/recipe-item/recipe-item.component';
import { RecipeListComponent } from '../components/recipes/recipe-list/recipe-list.component';
import { SharedModule } from '../shared/shared.module';
import { RecipesRoutingModule } from './recipes-routing.module';
import { RecipeVoteComponent } from '../components/recipes/recipe-vote/recipe-vote/recipe-vote.component';
import { RecipeUserComponent } from '../components/recipes/recipe-user/recipe-user/recipe-user.component';
import { RecipeInfoComponent } from '../components/recipes/recipe-info/recipe-info/recipe-info.component';
import { RecipeFilterComponent } from '../components/recipes/recipe-filter/recipe-filter.component';

@NgModule({
  declarations: [
    AddRecipeComponent,
    RecipeListComponent,
    RecipeItemComponent,
    RecipeDetailsComponent,
    CommentsComponent,
    NewCommentComponent,
    RecipeVoteComponent,
    RecipeUserComponent,
    RecipeInfoComponent,
    RecipeFilterComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    RecipesRoutingModule,
  ],
  exports: [RecipeListComponent, RecipeItemComponent],
})
export class RecipesModule {}
