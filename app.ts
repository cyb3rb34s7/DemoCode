import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FilterSidebarComponent } from './filter-sidebar/filter-sidebar.component';

@Component({
  selector: 'app-root',
  template: `
    <div class="container">
      <h1>Angular 17 Filter Application</h1>
      <button mat-raised-button color="primary" (click)="openFilterSidebar()">Filters</button>
      <!-- Your main content here -->
    </div>
  `,
  styles: [`
    .container {
      padding: 20px;
    }
  `]
})
export class AppComponent {
  constructor(private dialog: MatDialog) {}

  openFilterSidebar() {
    this.dialog.open(FilterSidebarComponent, {
      position: { right: '0' },
      height: '100%',
      width: '300px',
      panelClass: 'filter-sidebar-dialog'
    });
  }
}