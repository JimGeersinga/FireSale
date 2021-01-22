import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CreateAuctionDTO } from 'src/app/shared/models/createAuctionDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';
import { AuctionState } from 'src/app/shared/enums/auction-state.enum';
import { ThrowStmt } from '@angular/compiler';
import { YesNoDialogComponent } from 'src/app/shared/components/yes-no-dialog/yes-no-dialog.component';
import { AuctionUtil } from 'src/app/shared/auctionUtil';
import { WebSocketService } from 'src/app/shared/services/websocket.service';
import { AuctionMessageResponseType } from 'src/app/shared/models/webSocketAuctionMessage';
import { WinningInformationDTO } from 'src/app/shared/models/winningInformationDto';


@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  public auction: AuctionDTO;
  public winningInformation: WinningInformationDTO;
  public isAdmin = false;
  public isOwner = false;
  public isWinner = false;

  public auctionState = AuctionState;
  public state: AuctionState;

  private auctionId: number;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private auctionService: AuctionService,
    private userService: UserService,
    private dialog: MatDialog,
    private webSocketService: WebSocketService
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

      this.webSocketService.listenForAuctionUpdate<AuctionState>(this.auction.id).subscribe((message) => {
        if (message.responseType === AuctionMessageResponseType.UPDATED) {
          this.state = message.data;
        }
      });
    });
  }

  public getAuctionWinnerInformation(): void {
    this.auctionService.getWinningInformation(this.auctionId).subscribe(response => {
      this.winningInformation = response.data;
    });
  }

  public checkAuctionState(): void {
    this.state = AuctionUtil.getState(this.auction);
    this.userService.currentUser$.subscribe(currentUser => {
      if (!currentUser) {
        return;
      }
      this.isAdmin = this.userService.userIsAdmin$.value;
      this.isOwner = currentUser.id === this.auction.user.id;
      this.isWinner = currentUser.id === this.auction.finalBid?.userId;
      if (this.isWinner || this.isOwner && this.state === AuctionState.CLOSED) {
        this.getAuctionWinnerInformation();
      }
    });
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
    dialog.afterClosed().subscribe((result) => {
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
