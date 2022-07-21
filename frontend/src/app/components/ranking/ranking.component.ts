import { Component, OnInit } from '@angular/core';
import { RankingService } from 'src/app/services/RankingService/ranking.service';
import Ranker from 'src/app/shared/Ranker';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit {

  ranking: Ranker[] = [];

  constructor(private rankingService: RankingService) { }

  ngOnInit(): void {
    this.rankingService.getRanking()
      .subscribe((ranking) => this.ranking = ranking.content);
  }

}
