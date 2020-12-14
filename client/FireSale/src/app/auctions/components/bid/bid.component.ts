import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { WebSocketService } from 'src/app/core/services/websocket.service';
import { Util } from 'src/app/shared/util';
import { BidDTO } from '../../models/bidDto';
import { AuctionMessageResponseType } from '../../models/webSocketAuctionMessage';
import { AuctionService } from '../../shared/auction.service';


@Component({
  selector: 'app-bid',
  templateUrl: './bid.component.html',
  styleUrls: ['./bid.component.scss']
})
export class BidComponent implements OnInit {
  @Input() public auctionId: number;
  @Input() public endDate: Date;
  @Input() public minimumValue: number;
  public auctionError: String = "";
  @ViewChild("lastBidElement") lastBidRef: ElementRef;

  public bids: BidDTO[] = [];
  get lastBid(): BidDTO {
    return this.bids.sort((a, b) => new Date(b.created).getTime() - new Date(a.created).getTime())[0];
  }
  get auctionClosed(): boolean {
    return  (this.timeLeft - Date.now()) <= 0;
  }
  public timeLeft: number;
  public util = Util;

  constructor(
    private webSocketService: WebSocketService,
    private auctionService: AuctionService
  ) { }

  ngOnInit(): void {
    this.timeLeft = new Date(this.endDate).getTime();

    // Load initial bids
    this.auctionService.getBids(this.auctionId).subscribe((response) => {
      this.bids = response.data;
      this.minimumValue = this.getNextBidValue();
    });

    // Listen for newly placed bids
    this.webSocketService.listenForAuctionUpdate<BidDTO>(this.auctionId).subscribe((message) => {
      if (message.responseType === AuctionMessageResponseType.BID_PLACED) {
        this.bids.push(message.data);
        this.minimumValue = this.getNextBidValue();
        this.highlightLastBid();
      }
    });
  }

  public placeBid(bid: number): void {
    this.auctionService.placeBid(this.auctionId, bid).subscribe(response => {
        console.log('bid placed');
      console.log(response);
      this.auctionError = "";

      },
      error => {
        console.log('bid error');
        console.log(error);
        this.auctionError = "U bent overboden of de veiling is gesloten.";
      },
      () => {
        console.log('bid complete');
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
    if(!this.lastBidRef) return;
    if (!this.lastBidRef.nativeElement.classList.contains("highlight")) {
      this.lastBidRef.nativeElement.classList.add("highlight");
    }
    setTimeout(() => {
      this.lastBidRef.nativeElement.classList.remove("highlight");
    },500);
  }


}
