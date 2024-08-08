import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  sidebarOpened = false;

  openSidebar() {
    this.sidebarOpened = !this.sidebarOpened;
  }
}