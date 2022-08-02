import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
              private bookService: BookService,
              private fb: FormBuilder) { }

  bookForm: FormGroup;
  selectedGenre: string;

  genres: string[];

  @ViewChild("bForm") settingFormDirective: any;

  formErrors: any = {
    'name': '',
    'pages': '',
    'genre': '',
    'urlImg': '',
  }

  validationMessages: any = {
    'name': {
      'required':'Preenchimento Obrigatório',
      'minlength': 'Nome de usuário precisa ter mais do que 3 caracteres!',
      'maxlength': 'Nome de usuário precisa ter menos do que 255 caracteres!',
    },
    'pages':{
      'required':'Preenchimento Obrigatório',
    },
    'genre':{
      'required':'Selecione um gênero',
    }
  };

  

  ngOnInit(): void {
    this.bookService.getGenres()
      .subscribe((res) => this.genres = res);
    
    this.createForm();
  }

  onSubmit(){
    const genreNumber = this.genres.indexOf(this.bookForm.value.genre) + 1;
    const book = {
      ...this.bookForm.value,
      genre: genreNumber
    }

    this.bookService.addNewBook(book)
        .subscribe((res) => {
          this.dialogRef.close(res)
        });
  }

  createForm(): void{
    this.bookForm = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      pages: ["", Validators.required],
      genre: ['', [Validators.required]],
      urlImg: [""]
    })

    this.bookForm.valueChanges.subscribe(data => this.onValueChanged(data));
    
    this.onValueChanged();
  }

  onValueChanged(data?: any){
    if(!this.bookForm) return;
    const form = this.bookForm;
    for(const field in this.formErrors){
      if(this.formErrors.hasOwnProperty(field)) {
        // Clear Previous error message (if any)
        this.formErrors[field] = '';

        const control = form.get(field);
        if(control && control.dirty && !control.valid){
          const messages = this.validationMessages[field];

          for(const key in control.errors){
            if(control.errors.hasOwnProperty(key)){
              this.formErrors[field] += messages[key] + ' ';
            }
          }
        }
      }
    }
  }
}
