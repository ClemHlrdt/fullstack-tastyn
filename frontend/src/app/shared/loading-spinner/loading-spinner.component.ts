import { Component } from '@angular/core';

@Component({
  selector: 'app-loading-spinner',
  template:
    '<div class="container mx-auto h-95"><div class="lds-ring"><div></div><div></div><div></div><div></div></div></div>',
  styleUrls: ['./loading-spinner.component.scss'],
})
export class LoadingSpinnerComponent {}
