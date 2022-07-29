import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { HeaderComponent } from 'src/app/components/header/header.component';
import { BookService } from 'src/app/services/BookService/book.service';
import { UserService } from 'src/app/services/UserService/user.service';
import Book, { PageBook } from 'src/app/shared/Book';
import Trophy from 'src/app/shared/Trophy';
import User from 'src/app/shared/User';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  books: Book[];
  genresList: string[];
  
  user: User;
  userBooks: Book[];

  bookPage: PageBook;
  totalPages: number;
  pageIndex: number;

  selectedGenre: string;
  searchInputValue: string;
  
  notFound: boolean;

  @ViewChild(HeaderComponent) header: any;

  constructor(
    private bookService: BookService,
    private userService: UserService,
    ) { }

  ngOnInit(): void {
    this.getUser();
    this.getGenres();
  }

  getUser(){
    this.userService.getYourself()
      .subscribe((userRes) => {
        this.user = userRes;
        this.getPageBooks(0);
      })
  }

  getPageBooks(page: number, title?: string, genre?: number){
    this.bookService.getPageBooks(page, title, genre)
    .subscribe((booksRes) => {
      this.bookPage = booksRes;
      this.totalPages = booksRes.totalPages;

      const booksContent = [];
      for(let b of booksRes.content){
        const users = b.users;
        
        b = {
          ...b,
          marked: !!users?.find(u => u.name === this.user.name)
        }
        booksContent.push(b);
      }
      this.books = booksContent;
      this.notFound = booksContent.length <= 0;
    });
  }

  getGenres(){
    this.bookService.getGenres()
    .subscribe((genresRes) =>  {
      this.genresList = genresRes
    });
  }

  getSelectedBook(id: number){
    const book:Book | undefined = this.books.find(b => b.id = id);
    console.log(book);
  }

  onClick(){
    this.header.progressBarTimer();
    const genreNumber = this.genresList.indexOf(this.selectedGenre) + 1;
    this.getPageBooks(this.pageIndex, this.searchInputValue , genreNumber);
  }

  getPaginatorData(event: PageEvent){
    this.pageIndex = event.pageIndex;
    this.onClick();
  }

}
