import { Component, Input, OnInit } from '@angular/core';
import { BookService } from 'src/app/services/BookService/book.service';
import User from 'src/app/shared/User';

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.css']
})
export class BookCardComponent implements OnInit {

  constructor(private bookService: BookService) { }
  
  numberUsers: number;

  readonly noImage: string = '../../../assets/imgs/unnamed.png';
  
  @Input() _bookId: string;
  @Input() _userId: string;
  @Input() _bookName: string;
  @Input() _pages: string;
  @Input() _genre: string;
  @Input() _urlImg: string;
  @Input() _users: string;
  @Input() _marked: string;

  markedBoolean: boolean;

  ngOnInit(): void {
    this.markedBoolean = (this._marked === 'true');
    this.numberUsers = Number(this._users);
  }

  noImageFunction(){
    this._urlImg = this.noImage;
  }

  onClickRead(){
    this.bookService.addBook(Number(this._userId), Number(this._bookId))
        .subscribe(() => {
          this.markedBoolean = true
        });
  }

  onClickRemove(){
    this.bookService.removeBook(Number(this._userId), Number(this._bookId))
        .subscribe(() => {
          this.markedBoolean = false
        });
  }
}
