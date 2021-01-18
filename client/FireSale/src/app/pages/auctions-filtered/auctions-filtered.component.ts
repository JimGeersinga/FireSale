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
        this.categoryName = 'Mijn biedingen';
      } else if (data.routeName === 'myFavourites') {
        this.categoryName = 'Mijn favorieten';
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
