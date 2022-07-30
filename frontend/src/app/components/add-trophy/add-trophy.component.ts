import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BookService } from 'src/app/services/BookService/book.service';
import { TrophyService } from 'src/app/services/TrophyService/trophy.service';
import Trophy from 'src/app/shared/Trophy';

@Component({
  selector: 'app-add-trophy',
  templateUrl: './add-trophy.component.html',
  styleUrls: ['./add-trophy.component.css']
})
export class AddTrophyComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddTrophyComponent>,
              private trophyService: TrophyService,
              private bookService: BookService) { }

  trophy: Trophy = {
    name: '',
    genre: '',
  }

  genres: string[];

  ngOnInit(): void {
    this.bookService.getGenres()
        .subscribe((res) => this.genres = res);
  }

  onSubmit(){    
    this.trophyService.addTrophy(this.trophy)
        .subscribe(() => this.dialogRef.close());
  }
}
