import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DarkModeService {
  isDarkMode: boolean = false;

  constructor() {}

  modifyMode() {
    this.isDarkMode = !this.isDarkMode;
    this.isDarkMode
      ? (localStorage.theme = 'dark')
      : (localStorage.theme = 'light');
    location.reload();
  }
}
