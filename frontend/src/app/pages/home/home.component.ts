import { Component, OnInit } from '@angular/core';
import { BookService } from 'src/app/services/BookService/book.service';
import { UserService } from 'src/app/services/UserService/user.service';
import Book from 'src/app/shared/Book';
import User from 'src/app/shared/User';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  books: Book[];
  userBooks: Book[];
  user: User;

  constructor(
    private bookService: BookService,
    private userService: UserService
    ) { }

  ngOnInit(): void {
  
    this.bookService.getAllBooks()
        .subscribe((books) => this.books = books.content);

    // this.userService.getUser()
    //     .subscribe((user: User) => {
    //       this.user = user
    //       this.userBooks = user.books
    //     });

  }
}
