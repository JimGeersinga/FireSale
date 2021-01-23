import { P } from '@angular/cdk/keycodes';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-auctions-filtered',
  templateUrl: './auctions-filtered.component.html',
  styleUrls: ['./auctions-filtered.component.scss']
})
export class AuctionsFilteredComponent implements OnInit {
  public filteredAuctions: AuctionDTO[] = [];

  public categoryName: string;

  constructor(
    private route: ActivatedRoute,
    private auctionService: AuctionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {

    this.route.data.subscribe(data => {
      if (data.routeName === 'myBids') {
        this.categoryName = 'Mijn geboden veilingen';
        this.auctionService.getBidded().subscribe(response => {
          this.filteredAuctions = response.data;
        });
      } else if (data.routeName === 'myWinnings') {
        this.categoryName = 'Mijn gewonnen veilingen';
        this.auctionService.getWinnings().subscribe(response => {
          this.filteredAuctions = response.data;
        });
      } else if (data.routeName === 'myFavourites') {
        this.categoryName = 'Mijn favorieten';
        this.auctionService.getFavourite().subscribe(response => {
          this.filteredAuctions = response.data;
        });
      } else if (data.routeName === 'myAuctions') {
        this.categoryName = 'Mijn veilingen';
        this.userService.currentUser$.subscribe(currentUser => {
          this.userService.getUserAuctions(currentUser.id).subscribe(response => {
            this.filteredAuctions = response.data;
          });
        });
      }
    });
  }

}
