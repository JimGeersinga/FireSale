import { Component, HostListener, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
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

  private toolbarHeight = document.querySelector('.mat-toolbar').clientHeight;
  private sectionPadding = 25;

  constructor() {}

  ngOnInit(): void {
  }

  @HostListener('window:scroll', ['$event'])
  public onWindowScroll(): void {
    const container: HTMLDivElement = document.querySelector('.auctions-view');
    const distanceToTop = container.getBoundingClientRect().top;
    const currentElement: HTMLDivElement = document.querySelector('.auction-list-header');
    const isFixed = (distanceToTop - this.sectionPadding <= this.toolbarHeight);
    currentElement.classList.toggle('sticky', isFixed);
  }
}
