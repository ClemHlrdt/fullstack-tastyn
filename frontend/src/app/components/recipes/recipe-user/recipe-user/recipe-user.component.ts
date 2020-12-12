import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-recipe-user',
  templateUrl: './recipe-user.component.html',
  styleUrls: ['./recipe-user.component.scss'],
})
export class RecipeUserComponent implements OnInit {
  @Input()
  user: User;

  constructor() {}

  ngOnInit(): void {}

  addLoaded(e) {
    e.target.classList.add('loaded');
  }
}
