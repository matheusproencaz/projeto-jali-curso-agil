import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  loggedIn: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  logout(){
    this.loggedIn = false;
  }

}
