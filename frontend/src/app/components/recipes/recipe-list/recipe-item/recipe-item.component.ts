import { Component, OnInit, Input } from '@angular/core';

import { Recipe } from 'src/app/models/recipe.model';
@Component({
  selector: 'app-recipe-item',
  templateUrl: './recipe-item.component.html',
  styleUrls: ['./recipe-item.component.scss'],
})
export class RecipeItemComponent implements OnInit {
  constructor() {}
  @Input() recipe: Recipe;
  @Input() index: number;
  defaultImage = '/assets/img/recipe-placeholder.jpg';
  defaultAvatar = '/assets/img/avatar.jpg';
  ngOnInit(): void {}

  addLoaded(e) {
    e.target.classList.add('loaded');
  }
}
