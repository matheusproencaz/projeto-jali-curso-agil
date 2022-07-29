import { Injectable } from '@angular/core';
import { HttpClient }  from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageUser } from 'src/app/shared/User';

@Injectable({
  providedIn: 'root'
})

export class RankingService {

  constructor(private http: HttpClient) { }

  getRanking(page: number): Observable<any>{
    return this.http.get<PageUser> (`${environment.apiUrl}/ranking?page=${page}`);
  }

}
