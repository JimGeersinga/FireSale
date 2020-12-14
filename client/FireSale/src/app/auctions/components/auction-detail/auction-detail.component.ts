import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuctionService } from '../../shared/auction.service';
import { AuctionDTO } from '../../models/auctionDTO';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  private id: number;
  public model$: Observable<ApiResponse<AuctionDTO>>;
  items: Array<any> = [{ name: 'assets/images/headphone1.jfif' }, { name: 'assets/images/headphone2.jfif' }, { name: 'assets/images/headphone3.jfif' }, { name: 'assets/images/headphone4.jfif' }];
  timeLeft: number;

  constructor(
    private route: ActivatedRoute,
    private auctionService: AuctionService
  ) {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.model$ = this.auctionService.getSingle(this.id);
    });
  }

  ngOnInit(): void {

  }
}
