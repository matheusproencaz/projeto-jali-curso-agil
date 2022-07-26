import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { ExceptionHandlerService } from '../ExceptionHandler/exception-handler.service';
import { environment } from 'src/environments/environment'; 
import User, { UserLogin } from 'src/app/shared/User';
import { Router } from '@angular/router';
import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public token: string;
  userName: string;
  expirationDate: number;

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorMsg: ExceptionHandlerService,) {
      this.token = this.getToken()!;
      this.userName = this.getUserName()!;
      this.expirationDate = Number(this.getExpirationDate()!);
    }

  login(user: UserLogin): Observable<boolean> {
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

  private storageToken(res: any | null){
    this.token = res.headers.get('Authorization');
    
    const payload: any = jwtDecode(this.token);
    this.userName = payload.sub;
    this.expirationDate = payload.exp;

    localStorage.setItem('user', this.userName);
    localStorage.setItem('expirationDate', this.expirationDate.toString())
    return localStorage.setItem('token', this.token);
  }

  public getToken(){
    return localStorage.getItem('token');
  }

  public getUserName(){
    return localStorage.getItem('user');
  }

  public getExpirationDate(){
    return localStorage.getItem('expirationDate');
  }

  isLoggedIn(){
    return !!this.getToken();
  }

  logout(){
    this.token = '';
    this.userName = '';
    this.expirationDate = 0;
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('expirationDate');
    this.router.navigate(["/login"]);
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

  registry(user: UserLogin){
    return this.http.post<boolean>(`${environment.apiUrl}/users`, user)
      .pipe(
        map(() => true),
        catchError(err => {
          this.errorMsg.handleError(err);
          return of(false);
        })
      );
  }
} 
