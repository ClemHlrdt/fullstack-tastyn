import { Component, OnInit } from '@angular/core';
import { DarkModeService } from '../../services/dark-mode/dark-mode.service';

@Component({
  selector: 'app-dark-mode',
  templateUrl: './dark-mode.component.html',
  styleUrls: ['./dark-mode.component.scss'],
})
export class DarkModeComponent implements OnInit {
  isDarkMode: boolean;

  constructor(private darkModeService: DarkModeService) {}

  ngOnInit(): void {
    this.isDarkMode = this.darkModeService.isDarkMode;
  }

  handleClick() {
    this.darkModeService.modifyMode();
  }
}
