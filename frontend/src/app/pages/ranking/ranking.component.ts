import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { RankingService } from 'src/app/services/RankingService/ranking.service';
import Ranker from 'src/app/shared/Ranker';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {

  ranking: Array<Ranker> = [];
  
  numberPage: number;
  totalPages: number;
  pageIndex: number = 0;

  constructor(private rankingService: RankingService) { }

  ngOnInit(): void {
    this.getRanking(0);
  }

  getRanking(page: number){
    this.rankingService.getRanking(page)
    .subscribe((res) => {
      this.numberPage = res.number;
      this.totalPages = res.totalPages;
      this.ranking = res.content;
    });
  }

  getPaginatorData(event: PageEvent){
    this.pageIndex = event.pageIndex;
    this.getRanking(this.pageIndex);
  }
}
