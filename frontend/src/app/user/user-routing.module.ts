import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { AuthGuard } from '../router/auth.guard';
import { RouterModule } from '@angular/router';
import { UserProfileComponent } from '../components/user/user-profile/user-profile.component';
import { RecipeListComponent } from '../components/recipes/recipe-list/recipe-list.component';
import { UserEditComponent } from '../components/user/user-edit/user-edit.component';

import { UserResolve } from '../services/user/user.resolver';
const routes: Routes = [
  {
    path: '',
    canActivate: [AuthGuard],
    children: [
      {
        path: ':id',
        component: UserProfileComponent,
        resolve: {
          user: UserResolve,
        },
      },
      {
        path: ':id/edit',
        component: UserEditComponent,
        resolve: {
          user: UserResolve,
        },
      },
      {
        path: ':id/recipes',
        component: RecipeListComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
