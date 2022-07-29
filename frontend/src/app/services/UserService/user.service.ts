import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import User, { UserUpdate } from 'src/app/shared/User';
import { environment } from 'src/environments/environment';
import { ExceptionHandlerService } from '../ExceptionHandler/exception-handler.service';
import { LoginService } from '../LoginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private loginService: LoginService,
    private erroMsg: ExceptionHandlerService,
  ) { }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${environment.apiUrl}/users/${id}`)
        .pipe(catchError(this.erroMsg.handleError));
  }

  getYourself(): Observable<User>{
    const id = this.loginService.getUserId();
    return this.http.get<User>(`${environment.apiUrl}/users/${id}`)
          .pipe(catchError(this.erroMsg.handleError));
  }

  updateUser(user: UserUpdate):Observable<boolean>{
    return this.http.put<boolean>(`${environment.apiUrl}/users/${user.id}`, user)
      .pipe(catchError(this.erroMsg.handleError));
  }
}
