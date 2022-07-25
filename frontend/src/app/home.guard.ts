import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from './services/LoginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class HomeGuard implements CanActivate, CanLoad {
  
  constructor(private loginService: LoginService, private router: Router){}

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canLoad();
  }
  
  canLoad(): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if(!this.loginService.isLoggedIn()){
      this.router.navigate(['/login']);
    }

    return this.loginService.isLoggedIn();
  }
}
