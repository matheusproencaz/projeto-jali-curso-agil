import { Component, Input, OnInit } from '@angular/core';
import User from 'src/app/shared/User';

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.css']
})
export class BookCardComponent implements OnInit {

  constructor() { }

  @Input() bookName: string;
  @Input() pages: string;
  @Input() genre: string;
  @Input() urlImg: string;
  @Input() users: User[];

  ngOnInit(): void {
  }

}
