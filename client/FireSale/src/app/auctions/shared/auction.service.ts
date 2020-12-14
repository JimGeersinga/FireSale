import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';
import { AuctionDTO } from '../models/auctionDTO';

@Injectable({
  providedIn: 'root'
})
export class AuctionService {
  private baseUrl = 'auctions';
  constructor(private api: ApiService) {
  }

  public get(): Observable<any> {
    const response = this.api.get(`${this.baseUrl}`);
    return response;
  }

  public getSingle(id: number): Observable<any> {
    const response = this.api.get(`${this.baseUrl}/${id}`);
    return response;
  }

  public post(auction: AuctionDTO): Observable<any> {
    return this.api.post(`${this.baseUrl}`, auction);
  }

  public placeBid(auctionId: number, value: number): Observable<any> {
    return this.api.post(`${this.baseUrl}/${auctionId}/bids`, {
      value
    });
  }
  public getBids(auctionId: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${auctionId}/bids`);
  }
}
