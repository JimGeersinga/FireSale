import { Component, Input, OnInit } from '@angular/core';
import { AuctionDTO } from '../../models/auctionDTO';

@Component({
  selector: 'app-auction-list-item',
  templateUrl: './auction-list-item.component.html',
  styleUrls: ['./auction-list-item.component.scss']
})
export class AuctionListItemComponent implements OnInit {
  @Input() model: AuctionDTO;
  public timeLeft: number;
  public lessThan2Minutes: boolean = false;
  interval: any;
  constructor() { }

  ngOnInit(): void {
    this.timeLeft = this.timeDifference(new Date(), this.model.end);
    this.startTimer();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if(this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        this.interval.clear();
      }
    },1000)
  }


  timeDifference(start: Date, end: Date): number {
    return Math.floor((end.getTime() - start.getTime()) / 1000);
  }
}
