import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dark-mode',
  templateUrl: './dark-mode.component.html',
  styleUrls: ['./dark-mode.component.scss'],
})
export class DarkModeComponent implements OnInit {
  isDarkMode: boolean = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    let theme: string = localStorage.getItem('theme');
    theme == 'dark' || theme == null
      ? (this.isDarkMode = true)
      : (this.isDarkMode = false);
  }

  handleClick() {
    this.isDarkMode = !this.isDarkMode;
    this.modifyMode();
  }

  modifyMode() {
    this.isDarkMode
      ? (localStorage.theme = 'dark')
      : (localStorage.theme = 'light');
    location.reload();
  }
}
