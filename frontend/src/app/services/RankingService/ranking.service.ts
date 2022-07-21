import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders }  from '@angular/common/http';
import Ranker from 'src/app/shared/Ranker';
import { Observable } from 'rxjs';
import { baseURL } from 'src/app/shared/baseurl';

@Injectable({
  providedIn: 'root'
})

export class RankingService {

  constructor(private http: HttpClient) { }

  getRanking(): Observable<any>{
    return this.http.get<any> (`${baseURL}/ranking`);
  }

}
