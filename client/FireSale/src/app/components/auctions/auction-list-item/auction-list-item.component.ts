import { Component, Input, OnInit } from '@angular/core';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { BidDTO } from 'src/app/shared/models/bidDto';
import { DisplayType } from 'src/app/shared/enums/display-type.enum';
import { AuctionMessageResponseType } from 'src/app/shared/models/webSocketAuctionMessage';
import { WebSocketService } from 'src/app/shared/services/websocket.service';
import { Util } from 'src/app/shared/util';
import { AuctionUtil } from 'src/app/shared/auctionUtil';
import { AuctionState } from 'src/app/shared/enums/auction-state.enum';
import { Observable } from 'rxjs';
import { UserDTO } from 'src/app/shared/models/userDto';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-auction-list-item',
  templateUrl: './auction-list-item.component.html',
  styleUrls: ['./auction-list-item.component.scss']
})
export class AuctionListItemComponent implements OnInit {
  @Input() public model: AuctionDTO;
  @Input() public showInCard = true;
  @Input() public displayType: DisplayType;

  public timeLeft = 0;
  public auctionValue: number = null;
  public util = Util;

  public displayTypeEnum = DisplayType;
  public auctionState = AuctionState;
  public state: AuctionState;
  public isFavorite: boolean;

  public currentUser$: Observable<UserDTO>;

  constructor(
    private webSocketService: WebSocketService,
    private userService: UserService
    ) { }

  ngOnInit(): void {
    if (this.model === null) { return; }

    this.currentUser$ = this.userService.currentUser$;

    this.state = AuctionUtil.getState(this.model);
    if (this.state === AuctionState.SCHEDULED) {
      this.timeLeft = new Date(this.model.startDate).getTime();
    } else if (this.state === this.auctionState.RUNNING) {
      this.timeLeft = new Date(this.model.endDate).getTime();

    }

    let highestBid = this.model.minimalBid;
    if (this.model.bids && this.model.bids.length > 0) {
      highestBid = this.model.bids?.reduce((prev, next) => (prev.value > next.value) ? prev : next).value;
    }
    this.auctionValue = highestBid || 0;

    // Listen for newly placed bids
    this.webSocketService.listenForAuctionUpdate<any>(this.model.id).subscribe((message) => {
      if (message.responseType === AuctionMessageResponseType.BID_PLACED) {
        this.auctionValue = message.data.value;
      } else  if (message.responseType === AuctionMessageResponseType.UPDATED) {
        this.state = message.data;
      }
    });
  }

  public toggleFavorite($event): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.isFavorite = !this.isFavorite;


  }
}

