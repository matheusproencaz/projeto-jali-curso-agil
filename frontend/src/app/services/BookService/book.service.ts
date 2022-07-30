import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import Book, { PageBook } from 'src/app/shared/Book';
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

  getPageBooks(page: number, title?: string, genre?: number): Observable<PageBook> {
    
    if(!page){
      page = 0;
    }

    let url = `${environment.apiUrl}/books?page=${page}`;

    if(title){
      url += `&name=${title}`;
    }
    
    if(genre){
      url += `&genre=${genre}`
    }

    return this.http.get<PageBook>
        (url)
        .pipe(catchError(this.erroMsg.handleError));
  }

  getGenres(): Observable<string[]>{
    return this.http.get<string[]>(`${environment.apiUrl}/books/genres`)
                    .pipe(catchError(this.erroMsg.handleError));
  }

  getBook(id: number): Observable<Book>{
    return this.http.get<Book>(`${environment.apiUrl}/books/${id}`)
                    .pipe(catchError(this.erroMsg.handleError));
  }

  addBook(idUser: number, idBook: number): Observable<any> {
    return this.http.patch(`${environment.apiUrl}/users/${idUser}/addBook/${idBook}`, null)
                    .pipe(catchError(this.erroMsg.handleError));
  }

  removeBook(idUser: number, idBook: number): Observable<any> {
    return this.http.patch(`${environment.apiUrl}/users/${idUser}/removeBook/${idBook}`, null)
                    .pipe(catchError(this.erroMsg.handleError));
  }

  addNewBook(newBook: Book){
    return this.http.post(`${environment.apiUrl}/books`, newBook)
                    .pipe(catchError(this.erroMsg.handleError));
  }

  deleteBook(bookId: number){
    return this.http.delete(`${environment.apiUrl}/books/${bookId}`)
                    .pipe(catchError(this.erroMsg.handleError));
  }
}
