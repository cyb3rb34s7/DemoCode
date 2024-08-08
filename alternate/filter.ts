import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-filter-sidebar',
  templateUrl: './filter-sidebar.component.html',
  styleUrls: ['./filter-sidebar.component.css']
})
export class FilterSidebarComponent {
  filterForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      fromDate: [null],
      toDate: [null]
    });
  }

  get fromDate() {
    return this.filterForm.get('fromDate');
  }

  get toDate() {
    return this.filterForm.get('toDate');
  }

  applyFilter() {
    // Logic to filter data based on selected dates
    const { fromDate, toDate } = this.filterForm.value;
    console.log('Filter dates:', fromDate, toDate);
  }
}