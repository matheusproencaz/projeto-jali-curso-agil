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

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
      this.loginService
        .adminRequest()
        .subscribe(res => {
          this.admin = res
        })

       this.nomeUsuario = this.loginService.userName;
  }

  logout(){
    this.admin = false;
    this.loginService.logout();
  }

}
