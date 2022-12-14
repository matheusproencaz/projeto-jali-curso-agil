import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/LoginService/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  admin: boolean;
  nomeUsuario: string;

  progressBar: number = 0;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
      this.loginService
        .adminRequest()
        .subscribe(res => {
          this.admin = res
        })

       this.nomeUsuario = this.loginService.userName;

       this.progressBarTimer();
  }

  logout(){
    this.admin = false;
    this.loginService.logout();
  }

  progressBarTimer(){
    this.progressBar = 0;
    setTimeout(() => {
      const timer = setInterval(() => {
        this.progressBar += 1;
  
        if(this.progressBar >= 100){
          clearInterval(timer);
        }
      }, 1)

    }, 300);
  }
}
