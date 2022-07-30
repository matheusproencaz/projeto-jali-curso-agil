import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExceptionHandlerService } from 'src/app/services/ExceptionHandler/exception-handler.service';
import { LoginService } from 'src/app/services/LoginService/login.service';
import { UserLogin } from 'src/app/shared/User';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  loginForm: FormGroup;

  @ViewChild('lForm') loginFormDirective: any;

  formErrors: any = {
    'name': '',
    'password': '',
  }
  
  validationMessages: any = {
    'name': {
      'required':'Digite o nome de usuário',
      'minlength': 'Nome de usuário precisa ter mais do que 5 caracteres!',
      'maxlength': 'Nome de usuário precisa ter menos do que 32 caracteres!',
    },
    'password': {
      'required' : 'Digite uma nova senha!',
      'minlength': 'Senha precisa ter mais do que 8 caracteres!',
      'maxlength': 'Senha precisa ter menos do que 120 caracteres!!',
    },
  };

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router, private handler: ExceptionHandlerService) { }

  ngOnInit(): void {
    this.createForm();
  }

  onSubmit(){
    this.loginService.login(this.loginForm.value)
    .subscribe(
      success => {
        if(success){
          this.router.navigate(['/home'])
        }
      });
  }

  registry(){
    console.log(this.loginForm.value)
    this.loginService.registry(this.loginForm.value)
    .subscribe(res => {
      if(res){
        alert("Usuário Cadastrado! Já pode logar!");
      }
    });
  }

  createForm(): void{
    this.loginForm = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(5), Validators.maxLength(32)]],
      password: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(120)]]
    })

    this.loginForm.valueChanges.subscribe(data => this.onValueChanged(data));
    
    this.onValueChanged();
  }

  onValueChanged(data?: any){
    if(!this.loginForm) return;
    const form = this.loginForm;
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
