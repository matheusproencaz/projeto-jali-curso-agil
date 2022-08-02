import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { BookService } from 'src/app/services/BookService/book.service';
import { TrophyService } from 'src/app/services/TrophyService/trophy.service';

@Component({
  selector: 'app-add-trophy',
  templateUrl: './add-trophy.component.html',
  styleUrls: ['./add-trophy.component.css']
})
export class AddTrophyComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AddTrophyComponent>,
              private trophyService: TrophyService,
              private bookService: BookService,
              private fb: FormBuilder) { }


  trophyForm: FormGroup;
  selectedGenre: string;
            
  genres: string[];

  ngOnInit(): void {
    this.bookService.getGenres()
      .subscribe((res) => this.genres = res);
    
    this.createForm();
  }

  @ViewChild("tForm") settingFormDirective: any;

  formErrors: any = {
    'name': '',
    'genre': '',
  }

  validationMessages: any = {
    'name': {
      'required':'Preenchimento Obrigatório',
      'minlength': 'Nome de usuário precisa ter mais do que 3 caracteres!',
      'maxlength': 'Nome de usuário precisa ter menos do que 255 caracteres!',
    },
    'genre':{
      'required':'Selecione um gênero',
    }
  };
  
  onSubmit(){
    const genreNumber = this.genres.indexOf(this.trophyForm.value.genre) + 1;
    const trophy = {
      ...this.trophyForm.value,
      genre: genreNumber
    }

    this.trophyService.addTrophy(trophy)
        .subscribe((res) => {
          this.dialogRef.close(res)
        });
  }

  createForm(): void{
    this.trophyForm = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      genre: ['', [Validators.required]],
    })

    this.trophyForm.valueChanges.subscribe(data => this.onValueChanged(data));
    this.onValueChanged();
  }

  onValueChanged(data?: any){
    if(!this.trophyForm) return;
    const form = this.trophyForm;
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
