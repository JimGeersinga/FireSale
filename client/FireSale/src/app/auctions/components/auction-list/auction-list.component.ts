import { Component, OnInit } from '@angular/core';
import { AuctionDTO } from '../../models/auctionDTO';
import { AuctionService } from '../../shared/auction.service';

@Component({
  selector: 'app-auction-list',
  templateUrl: './auction-list.component.html',
  styleUrls: ['./auction-list.component.scss']
})
export class AuctionListComponent implements OnInit {
  public auctions: any = [];
  


  constructor(
    private auctionService: AuctionService
  ) { }

  ngOnInit(): void {
    this.auctionService.get().subscribe({
      next(response) {
        this.auctions = response.data;
      }});
    
  }

  


}
