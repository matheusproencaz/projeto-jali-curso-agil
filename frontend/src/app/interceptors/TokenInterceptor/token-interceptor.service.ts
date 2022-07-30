import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { LoginService } from 'src/app/services/LoginService/login.service';
import { environment } from 'src/environments/environment';
import { ExceptionHandlerService } from 'src/app/services/ExceptionHandler/exception-handler.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{
  
  private urlsNotUse: Array<string> = ["/login", "/users/admin"];
  private token: string;

  constructor(
    private loginService: LoginService,
    private errorHandler: ExceptionHandlerService,
  ) { 
      this.token = this.loginService.token;
    }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler): Observable<HttpEvent<any>> {

    if(this.urlsNotUseFunction(req)){
      return next.handle(req);
    }

    let modifiedRequest = req.clone({
      setHeaders: {
        'Authorization': `${this.loginService.getToken()}`,
      }
    });

    return next.handle(modifiedRequest).pipe(
      catchError(err => this.errorHandler.handleError(err)));
  }

  private urlsNotUseFunction(req: HttpRequest<any>){
    for(let url of this.urlsNotUse){
      if(req.url === environment.apiUrl+url){
        return true;
      }
    }
    return false;
  }
}
