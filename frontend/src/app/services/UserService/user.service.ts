import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import User from 'src/app/shared/User';
import { environment } from 'src/environments/environment';
import { ExceptionHandlerService } from '../ExceptionHandler/exception-handler.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private erroMsg: ExceptionHandlerService,
  ) { }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${environment.apiUrl}/books/${id}`)
        .pipe(catchError(this.erroMsg.handleError));
  }
}
