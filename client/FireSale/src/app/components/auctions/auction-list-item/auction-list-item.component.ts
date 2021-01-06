import { Component, Input, OnInit } from '@angular/core';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { BidDTO } from 'src/app/shared/models/bidDto';
import { DisplayType } from 'src/app/shared/models/display-type.enum';
import { AuctionMessageResponseType } from 'src/app/shared/models/webSocketAuctionMessage';
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
  public latestBid: number = null;
  public util = Util;
  public displayTypeEnum = DisplayType;

  constructor(private webSocketService: WebSocketService) { }

  ngOnInit(): void {
    if (this.model === null) { return; }

    this.timeLeft = new Date(this.model.endDate).getTime();
    this.latestBid = this.model.minimalBid || 0;

    // Listen for newly placed bids
    this.webSocketService.listenForAuctionUpdate<BidDTO>(this.model.id).subscribe((message) => {
      if (message.responseType === AuctionMessageResponseType.BID_PLACED) {
        this.latestBid = message.data.value;
      }
    });
  }
}
