import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import Trophy from 'src/app/shared/Trophy';
import { environment } from 'src/environments/environment';
import { ExceptionHandlerService } from '../ExceptionHandler/exception-handler.service';

@Injectable({
  providedIn: 'root'
})
export class TrophyService {

  constructor(private http: HttpClient,
              private handlerHTTP: ExceptionHandlerService ) { }

  getAlltrophies(): Observable<Trophy[]>{
    return this.http.get<Trophy[]>(`${environment.apiUrl}/trophies`)
                    .pipe(catchError(this.handlerHTTP.handleError));
  }

  getTrophyById(trophyId: number): Observable<Trophy>{
    return this.http.get<Trophy>(`${environment.apiUrl}/trophies/${trophyId}`)
                    .pipe(catchError(this.handlerHTTP.handleError));
  }

  addTrophy(trophy: Trophy): Observable<Trophy> {
    return this.http.post<Trophy>(`${environment.apiUrl}/trophies`, trophy)
    .pipe(catchError(this.handlerHTTP.handleError)); 
  }

  removeTrophy(trophyId: number): Observable<boolean> {
    return this.http.delete<boolean>(`${environment.apiUrl}/trophies/${trophyId}`)
    .pipe(map(() => true),
          catchError((err) => of(false))); 
  }
}
