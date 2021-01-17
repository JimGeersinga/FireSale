import { Component, Input, OnInit } from '@angular/core';
import { nextTick } from 'process';
import { Observable } from 'rxjs/internal/Observable';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { BidDTO } from 'src/app/shared/models/bidDto';
import { DisplayType } from 'src/app/shared/models/display-type.enum';
import { AuctionMessageResponseType } from 'src/app/shared/models/webSocketAuctionMessage';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { WebSocketService } from 'src/app/shared/services/websocket.service';
import { Util } from 'src/app/shared/util';

@Component({
  selector: 'app-auction-list-item',
  templateUrl: './auction-list-item.component.html',
  styleUrls: ['./auction-list-item.component.scss']
})
export class AuctionListItemComponent implements OnInit {
  @Input() public model: AuctionDTO;
  @Input() public showInCard = true;
  @Input() public displayType: DisplayType;

  public timeLeft: number;
  public auctionValue: number = null;
  public util = Util;
  public displayTypeEnum = DisplayType;

  constructor(private webSocketService: WebSocketService) { }

  ngOnInit(): void {
    if (this.model === null) { return; }

    this.timeLeft = new Date(this.model.endDate).getTime();

    const highestBid = this.model.bids?.reduce((prev, next) => (prev.value > next.value) ? prev : next);
    this.auctionValue = highestBid?.value || this.model.minimalBid || 0;

    // Listen for newly placed bids
    this.webSocketService.listenForAuctionUpdate<BidDTO>(this.model.id).subscribe((message) => {
      if (message.responseType === AuctionMessageResponseType.BID_PLACED) {
        this.auctionValue = message.data.value;
      }
    });
  }
}