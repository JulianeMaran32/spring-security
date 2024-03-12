import { Component } from '@angular/core';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  user = new User();

  constructor() {

  }

  ngOnInit() {
    if(sessionStorage.getItem('userdetails')) {
      this.user = JSON.parse(sessionStorage.getItem('userdetails') || "");
    }
  }

}
