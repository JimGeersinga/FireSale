import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';
import { AuctionState } from 'src/app/shared/enums/auction-state.enum';
import { YesNoDialogComponent } from 'src/app/shared/components/yes-no-dialog/yes-no-dialog.component';
import { AuctionUtil } from 'src/app/shared/auctionUtil';


@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  public auction: AuctionDTO;
  public isAdmin = false;
  public isOwner = false;

  public auctionState = AuctionState;
  public state: AuctionState;

  private auctionId: number;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private auctionService: AuctionService,
    private userService: UserService,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.auctionId = params.id;
      this.loadAuction();
    });
  }

  public loadAuction(): void {
    this.auctionService.getSingle(this.auctionId).subscribe(response => {
      this.auction = response.data;
      this.checkAuctionState();
    });
  }

  public checkAuctionState(): void {
    this.userService.currentUser$.subscribe(currentUser => {
      if (!currentUser) {
        return;
      }
      this.isAdmin = this.userService.userIsAdmin$.value;
      this.isOwner = currentUser.id === this.auction.user.id;
    });

    this.state = AuctionUtil.getState(this.auction);
  }

  public cancelAuction(): void {
    const dialog = this.dialog.open(YesNoDialogComponent, { data: { title: 'Veiling annuleren', message: 'Weet u zeker dat u de veiling wilt annuleren?' } });
    dialog.afterClosed().subscribe((result) => {
      if (result) {
        this.auctionService.patch(this.auctionId, { status: 'CANCELLED' }).subscribe(() => {
          this.auction.status = 'CANCELLED';
          this.checkAuctionState();
        });
      }
    });
  }

  public deleteAuction(): void {
    const dialog = this.dialog.open(YesNoDialogComponent, { data: { title: 'Veiling verwijderen', message: 'Weet u zeker dat u de veiling wilt verwijderen?' } });
    dialog.afterClosed().subscribe(() => {
      this.auctionService.delete(this.auctionId).subscribe(() => {
        this.router.navigate(['/auctions']);
      });
    });
  }

  public toggleFeatured(): void {
    this.auctionService.patch(this.auctionId, { isFeatured: !this.auction.isFeatured }).subscribe(_ => {
      this.auction.isFeatured = !this.auction.isFeatured;
    });
  }
}
