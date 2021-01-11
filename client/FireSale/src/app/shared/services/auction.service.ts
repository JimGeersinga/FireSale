import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';
import { AuctionDTO } from '../models/auctionDto';
import { CreateAuctionDTO } from '../models/createAuctionDto';
import { FilterDTO } from '../models/filterDTO';

@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  private baseUrl = 'auctions';

  constructor(private api: ApiService) {
  }

  public getActive(pageIndex: number, pageSize: number): Observable<any> {
    let url = `${this.baseUrl}/started?`;

    const params: string[] = [];
    if (pageIndex) { params.push(`pageIndex=${pageIndex}`); }
    if (pageSize) { params.push(`pageSize=${pageSize}`); }
    url += params.join('&');

    const response = this.api.get(url);
    return response;
  }

  public getFeatured(): Observable<any> {
    const response = this.api.get(`${this.baseUrl}/featured`);
    return response;
  }

  public getFiltered(filter: FilterDTO): Observable<any> {
    const response = this.api.post(`${this.baseUrl}/filter`, filter);
    return response;
  }

  public getSingle(id: number): Observable<any> {
    const response = this.api.get(`${this.baseUrl}/${id}`);
    return response;
  }

  public post(auction: CreateAuctionDTO): Observable<any> {
    return this.api.post(`${this.baseUrl}`, auction);
  }

  public patch(auctionId: number, auction: CreateAuctionDTO): Observable<any> {
    return this.api.patch(`${this.baseUrl}/${auctionId}`, auction);
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
