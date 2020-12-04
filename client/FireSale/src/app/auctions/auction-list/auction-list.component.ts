import { Component, OnInit } from '@angular/core';
import { Auction } from './auction';
import { AUCTIONS } from './mock-auctions';

@Component({
  selector: 'app-auction-list',
  templateUrl: './auction-list.component.html',
  styleUrls: ['./auction-list.component.scss']
})
export class AuctionListComponent implements OnInit {

  auctions = AUCTIONS;
  selectedAuction: Auction;

  constructor() { }

  ngOnInit(): void {
  }

  onSelect(auction: Auction): void {
    this.selectedAuction = auction;
  }

}





// import { Component, OnInit } from '@angular/core';
// import { Hero } from '../hero';
// import { HEROES } from '../mock-heroes';

// @Component({
//   selector: 'app-heroes',
//   templateUrl: './heroes.component.html',
//   styleUrls: ['./heroes.component.css']
// })

// export class HeroesComponent implements OnInit {

//   heroes = HEROES;
//   selectedHero: Hero;

//   constructor() { }

//   ngOnInit() {
//   }

//   onSelect(hero: Hero): void {
//     this.selectedHero = hero;
//   }
// }