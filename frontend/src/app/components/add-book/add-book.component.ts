import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BookService } from 'src/app/services/BookService/book.service';
import Book from 'src/app/shared/Book';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddBookComponent>,
              private bookService: BookService) { }

  book: Book = {
    name: '',
    pages: 0,
    genre: '',
    urlImg: '',
  }

  genres: string[];

  ngOnInit(): void {
    this.bookService.getGenres()
        .subscribe((res) => this.genres = res);
  }

  onSubmit(){
    this.bookService.addNewBook(this.book)
        .subscribe(() => this.dialogRef.close());
  }
}
