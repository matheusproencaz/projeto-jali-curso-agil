import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { catchError, pipe, switchMap } from 'rxjs';
import { BookService } from 'src/app/services/BookService/book.service';
import Book from 'src/app/shared/Book';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  book: Book = {
    id: 0,
    name: '',
    pages: 0,
    genre: '',
    urlImg: '',
    marked: false,
    users: [],
  };

  notFound: boolean;

  constructor(private route: ActivatedRoute,
              private bookService: BookService) { }

  ngOnInit(): void {
    this.route.params
        .pipe(switchMap((params: Params) => {
          return this.bookService.getBook(params['id'])
        }))
        .subscribe((book) => {
            this.book = book
        },
        () => {this.notFound = true}
        );
  }
}
