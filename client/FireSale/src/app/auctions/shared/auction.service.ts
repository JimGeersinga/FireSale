import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/core/services/api.service';
import { AuctionDTO } from '../models/auctionDTO';

@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  private baseUrl = 'auctions';
  constructor(private api: ApiService) {
  }

  /**
   * get
   */
  public get(): AuctionDTO[] {
    let auctions: AuctionDTO[] = [];
    var now = new Date();
      for (let i = 0; i < 10; i++)
      {
        let adate = new Date(now.setMinutes(now.getMinutes() + i));
        auctions.push({
          name: 'Auction ' + i.toString(),
          description: 'Description ' + i.toString(),
          startDate: now,
          endDate: adate,
          minimalBid: 100 - i
        });
      }
    return auctions;
  }

  public getSingle(id:Number): AuctionDTO {
    var now = new Date();
    let adate = new Date(now.setMinutes(now.getMinutes() + (id as number)));
    return {
          name: 'Auction ' + id.toString(),
          description: 'Desription ' + id.toString(),
          startDate: now,
          endDate: adate,
          minimalBid: 100 - (id as number)
        };
  }
}
