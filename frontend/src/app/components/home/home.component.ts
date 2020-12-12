import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  leftImg = '/assets/img/landing-2.jpg';

  constructor() {}

  ngOnInit(): void {}
}
