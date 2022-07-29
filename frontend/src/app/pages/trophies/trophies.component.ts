import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/UserService/user.service';
import Trophy from 'src/app/shared/Trophy';

@Component({
  selector: 'app-trophies',
  templateUrl: './trophies.component.html',
  styleUrls: ['./trophies.component.css']
})
export class TrophiesComponent implements OnInit {

  trophies: Trophy[];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getYourself()
          .subscribe((user) => {
            this.trophies = user.trophies;
          });
  }
}
