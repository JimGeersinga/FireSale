import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuctionService } from '../../shared/auction.service';
import { AuctionDTO } from '../../models/auctionDTO';


@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  private id: number;
  public model: AuctionDTO;
  constructor(private route: ActivatedRoute, private auctionService: AuctionService){
    console.log('Called Constructor');
    this.route.queryParams.subscribe(params => {
        this.id = params['id'];
      this.auctionService.getSingle(this.id).subscribe({
        next(response) {
          this.model = response.data;
        }
      });
    });
}

  ngOnInit(): void {
  }


}
