import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders }  from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class RankingService {

  constructor(private http: HttpClient) { }

  getRanking(page: number | any): Observable<any>{
    return this.http.get<any> (`${environment.apiUrl}/ranking?page=${page}`);
  }

}
