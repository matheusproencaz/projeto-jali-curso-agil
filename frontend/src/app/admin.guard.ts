import { Injectable } from '@angular/core';
import { CanActivate, CanLoad, Router, UrlTree } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { LoginService } from './services/LoginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate, CanLoad {
  
  isAdmin: boolean;

  constructor(private loginService: LoginService, private router: Router){
   
  }
  
  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canLoad();
  }
  
  canLoad(): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
   
    if(!this.loginService.isLoggedIn()){
      this.router.navigate(['/login'])
    }

    return this.loginService.adminRequest().pipe(
      map(res => {
        
        if(!res){
          this.router.navigate(['/home']);
        }

        return (this.loginService.isLoggedIn() && res)
      }),
      catchError(() => of(false))
    )
  }
}
