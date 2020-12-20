import { Component, OnInit } from '@angular/core';
import { DarkModeService } from '../../services/dark-mode/dark-mode.service';

@Component({
  selector: 'app-dark-mode',
  templateUrl: './dark-mode.component.html',
  styleUrls: ['./dark-mode.component.scss'],
})
export class DarkModeComponent implements OnInit {
  //isDarkMode: boolean = false;

  constructor(private darkModeService: DarkModeService) {}

  ngOnInit(): void {
    let theme: string = localStorage.getItem('theme');
    theme == 'dark' || theme == null
      ? (this.darkModeService.isDarkMode = true)
      : (this.darkModeService.isDarkMode = false);
  }

  handleClick() {
    this.darkModeService.modifyMode();
  }
}
