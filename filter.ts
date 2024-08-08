import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-filter-sidebar',
  template: `
    <div class="filter-sidebar">
      <h2>Filters</h2>
      <mat-accordion>
        <mat-expansion-panel>
          <mat-expansion-panel-header>
            <mat-panel-title>Date Range</mat-panel-title>
          </mat-expansion-panel-header>
          <mat-form-field>
            <mat-label>From</mat-label>
            <input matInput [matDatepicker]="fromPicker" (dateChange)="onDateChange()">
            <mat-datepicker-toggle matSuffix [for]="fromPicker"></mat-datepicker-toggle>
            <mat-datepicker #fromPicker></mat-datepicker>
          </mat-form-field>
          <mat-form-field>
            <mat-label>To</mat-label>
            <input matInput [matDatepicker]="toPicker" (dateChange)="onDateChange()">
            <mat-datepicker-toggle matSuffix [for]="toPicker"></mat-datepicker-toggle>
            <mat-datepicker #toPicker></mat-datepicker>
          </mat-form-field>
        </mat-expansion-panel>
      </mat-accordion>
    </div>
  `,
  styles: [`
    .filter-sidebar {
      padding: 20px;
    }
    mat-form-field {
      width: 100%;
      margin-bottom: 15px;
    }
  `]
})
export class FilterSidebarComponent {
  constructor(private dialogRef: MatDialogRef<FilterSidebarComponent>) {}

  onDateChange() {
    // Implement your filter logic here
    console.log('Date changed, apply filter');
  }
}