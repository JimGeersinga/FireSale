import { Component, Input, OnInit } from '@angular/core';
import { WebSocketService } from 'src/app/core/services/websocket.service';
import { Util } from 'src/app/shared/util';
import { AuctionDTO } from '../../models/auctionDTO';
import { BidDTO } from '../../models/bidDto';
import { AuctionMessageResponseType } from '../../models/webSocketAuctionMessage';
import { DisplayType } from '../../shared/display-type.enum';

@Component({
  selector: 'app-auction-list-item',
  templateUrl: './auction-list-item.component.html',
  styleUrls: ['./auction-list-item.component.scss']
})
export class AuctionListItemComponent implements OnInit {
  @Input() public model: AuctionDTO;
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
