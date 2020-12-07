import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuctionDTO } from '../../models/auctionDTO';

@Component({
  selector: 'app-auction-list-item',
  templateUrl: './auction-list-item.component.html',
  styleUrls: ['./auction-list-item.component.scss']
})
export class AuctionListItemComponent implements OnInit {
  @Input() public model: AuctionDTO;

  public timeLeft: number;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.timeLeft = new Date(this.model.endDate).getTime();
  }
}
