import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UserProfileComponent } from '../components/user/user-profile/user-profile.component';
import { RecipesModule } from '../recipes/recipes.module';
import { SharedModule } from '../shared/shared.module';
import { UserRoutingModule } from './user-routing.module';
import { UserEditComponent } from '../components/user/user-edit/user-edit.component';

@NgModule({
  declarations: [UserProfileComponent, UserEditComponent],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    UserRoutingModule,
    RecipesModule,
  ],
  exports: [],
})
export class UserModule {}
