import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { AuctionState } from 'src/app/shared/enums/auction-state.enum';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { BidDTO } from 'src/app/shared/models/bidDto';
import { AuctionMessageResponseType } from 'src/app/shared/models/webSocketAuctionMessage';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';
import { WebSocketService } from 'src/app/shared/services/websocket.service';
import { Util } from 'src/app/shared/util';


@Component({
  selector: 'app-bid',
  templateUrl: './bid.component.html',
  styleUrls: ['./bid.component.scss']
})
export class BidComponent implements OnInit {
  @Input() public auction: AuctionDTO;
  @Input() public startDate: Date;
  @Input() public endDate: Date;
  @Input() public state: AuctionState;
  @Input() public minimumValue: number;

  public auctionError = '';
  @ViewChild('lastBidElement') lastBidRef: ElementRef;

  public bids: BidDTO[] = [];
  get lastBid(): BidDTO {
    return this.bids.sort((a, b) => new Date(b.created).getTime() - new Date(a.created).getTime())[0];
  }

  public isWinner = false;
  public isOwner = false;

  public timeLeft = 0;
  public util = Util;
  public auctionState = AuctionState;

  constructor(
    private webSocketService: WebSocketService,
    private auctionService: AuctionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    if (!this.auction) { return; }

    if (this.state === AuctionState.SCHEDULED) {
      this.timeLeft = new Date(this.startDate).getTime();
    }
    else if (this.state === AuctionState.RUNNING) {
      this.timeLeft = new Date(this.endDate).getTime();
    }

    this.userService.currentUser$.subscribe(currentUser => {
      if (!currentUser) {
        return;
      }

      this.isWinner = currentUser.id === this.auction.finalBid?.userId;
      this.isOwner = currentUser.id === this.auction.user.id;
    });

    // Load initial bids
    this.auctionService.getBids(this.auction.id).subscribe((response) => {
      this.bids = response.data;
      this.minimumValue = this.getNextBidValue();
    });

    // Listen for newly placed bids
    this.webSocketService.listenForAuctionUpdate<BidDTO>(this.auction.id).subscribe((message) => {
      if (message.responseType === AuctionMessageResponseType.BID_PLACED) {
        this.bids.push(message.data);
        this.minimumValue = this.getNextBidValue();
        this.highlightLastBid();
      }
    });
  }

  public placeBid(bid: number): void {
    this.auctionError = null;
    this.auctionService.placeBid(this.auction.id, bid).subscribe(response => {
      console.log('bid placed', response);
    },
      error => {
        console.log('bid error', error);
        this.auctionError = 'U bent overboden of de veiling is gesloten.';
      });
  }

  private getNextBidValue(): number {
    if (this.bids.length === 0) {
      return this.minimumValue;
    }
    const maxBid = this.bids.reduce((prev, curr) => prev.value > curr.value ? prev : curr);
    return maxBid.value + 1;
  }

  private highlightLastBid(): void {
    if (!this.lastBidRef) { return; }
    if (!this.lastBidRef.nativeElement.classList.contains('highlight')) {
      this.lastBidRef.nativeElement.classList.add('highlight');
    }
    setTimeout(() => {
      this.lastBidRef.nativeElement.classList.remove('highlight');
    }, 500);
  }


}
