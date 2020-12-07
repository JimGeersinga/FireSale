import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  constructor(private router: Router) { }

  ngOnInit(): void {
    console.log(this.model);
    this.timeLeft = this.timeDifference(new Date(), this.model.endDate);
    
    this.startTimer();
  }

  openDetails()
  {
    console.log('navigate');
    this.router.navigate(['/auctions/details'], { queryParams: { id:this.model.id} });
  }

  startTimer() {
    this.interval = setInterval(() => {
      if(this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        clearInterval(this.interval);
      }
    },1000)
    
  }


  timeDifference(start: Date, end: Date): number {
    return Math.floor((new Date(end).getTime() - start.getTime()) / 1000);
  }
}
