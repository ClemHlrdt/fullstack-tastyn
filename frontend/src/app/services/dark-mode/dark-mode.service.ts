import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DarkModeService {
  isDarkMode: boolean = false;

  constructor() {
    let theme: string = localStorage.getItem('theme');
    theme == 'dark' || theme == null
      ? (this.isDarkMode = true)
      : (this.isDarkMode = false);
  }

  modifyMode() {
    this.isDarkMode = !this.isDarkMode;
    this.isDarkMode
      ? (localStorage.theme = 'dark')
      : (localStorage.theme = 'light');
    location.reload();
  }
}
