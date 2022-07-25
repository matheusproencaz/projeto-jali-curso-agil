import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { ExceptionHandlerService } from '../ExceptionHandler/exception-handler.service';
import { environment } from 'src/environments/environment'; 
import User from 'src/app/shared/User';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  token: string;

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorMsg: ExceptionHandlerService,) {

      this.token = this.getCurrentUser()!;
    }

  login(user: User): Observable<boolean> {
    return this.http.post<boolean>(`${environment.apiUrl}/login`, user, {observe: 'response'})
      .pipe(
        tap(res => {
          this.storageToken(res);
        }),
        map(() => true),
        catchError(error => {
          this.errorMsg.handleError(error);
          return of(false);
        }));
  }

  private storageToken(token: any | null){
    this.token = token.headers.get('Authorization');
    return localStorage.setItem('currentUser', this.token);
  }

  registry(user: User){
    return this.http.post<boolean>(`${environment.apiUrl}/users`, user)
      .pipe(
        map(() => true),
        catchError(err => {
          this.errorMsg.handleError(err);
          return of(false);
        })
      );
  }

  logout(){
    this.token = '';
    localStorage.removeItem('currentUser');
    this.router.navigate(["/login"]);
  }

  public getCurrentUser() {
    return localStorage.getItem('currentUser');
  }

  isLoggedIn(){
    return !!this.getCurrentUser();
  }

  adminRequest(): Observable<boolean>{

    const options = {
      headers: new HttpHeaders({
        'Authorization': `${this.token}`
      }),
    }

    return this.http.get(`${environment.apiUrl}/users/admin`, options)
      .pipe(
          map(() => true),
          catchError(err => {
            return of(false);
    }));
  }
} 
