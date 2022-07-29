import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(private iconRegistry: MatIconRegistry,
              private sanitizer: DomSanitizer){

    const svgIcons: string[][] = [
      ['trophy', '../assets/imgs/trophy.svg'],
      ['books', '../assets/imgs/books-stack.svg'],
      ['plusSolid', '../assets/imgs/plusSolid.svg']
    ];

    svgIcons.map(svgInfo => this.addSvg(svgInfo[0], svgInfo[1]));
  }

  addSvg(name:string, url:string){
    this.iconRegistry.addSvgIcon(
      name,
      this.sanitizer.bypassSecurityTrustResourceUrl(url)
    );
  }
}
