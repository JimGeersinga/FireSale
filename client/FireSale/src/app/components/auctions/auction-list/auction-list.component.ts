import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { DisplayType } from 'src/app/shared/models/display-type.enum';

@Component({
  selector: 'app-auction-list',
  templateUrl: './auction-list.component.html',
  styleUrls: ['./auction-list.component.scss'],
})
export class AuctionListComponent implements OnInit {
  @Input() public auctions: AuctionDTO[] = null;
  @Input() public title = 'Alle veilingen';
  @Input() public showInCard = true;

  public displayTypeEnum = DisplayType;
  public displayType: DisplayType = DisplayType.LIST;

  constructor() {}

  ngOnInit(): void {
  }
}
