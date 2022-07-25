import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/LoginService/login.service';
import User from 'src/app/shared/User';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  errors: string[] | any = [];
  
  user: User = {
    name: '',
    password: '',
  }
  
  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.loginService.login(this.user)
    .subscribe(
      success => {
        if(success){
          this.router.navigate(['/home'])
        }
      });
  }

  registry(){
    this.loginService.registry(this.user)
    .subscribe(res => {
      if(res){
        alert("Usuário Cadastrado! Já pode logar!");
      }});
  }
}
