import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { PageBook } from 'src/app/shared/Book';
import { environment } from 'src/environments/environment';
import { ExceptionHandlerService } from '../ExceptionHandler/exception-handler.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(
    private http: HttpClient,
    private erroMsg: ExceptionHandlerService,
  ) { }

  getAllBooks(): Observable<PageBook> {
    return this.http.get<PageBook>(`${environment.apiUrl}/books`)
        .pipe(catchError(this.erroMsg.handleError));
  }
}
