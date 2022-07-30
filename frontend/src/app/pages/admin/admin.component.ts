import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddBookComponent } from 'src/app/components/add-book/add-book.component';
import { AddTrophyComponent } from 'src/app/components/add-trophy/add-trophy.component';
import { BookService } from 'src/app/services/BookService/book.service';
import { TrophyService } from 'src/app/services/TrophyService/trophy.service';
import Book from 'src/app/shared/Book';
import Trophy from 'src/app/shared/Trophy';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  books: Book[];
  trophies: Trophy[];

  constructor(private bookService: BookService,
              private trophyService: TrophyService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.bookService.getListBooks()
        .subscribe((res) => {
          this.books = res
          console.log(res);
        })

    this.trophyService.getAlltrophies()
        .subscribe((res) => this.trophies = res);
  }

  deleteBook(id: number){
    this.bookService.deleteBook(id)
        .subscribe((res) => {
          if(res) this.books = this.books.filter(x => x.id != id);
        });
  }

  deleteTrophy(id: number){
    this.trophyService.deleteTrophy(id)
        .subscribe((res) => {
          if(res) this.trophies = this.trophies.filter(x => x.id != id);
        });
  }

  addBook(){
    this.dialog.open(AddBookComponent, {width: '500px', height: 'auto'})
  }

  addTrophy(){
    this.dialog.open(AddTrophyComponent, {width: '500px', height: 'auto'})
  }
}


