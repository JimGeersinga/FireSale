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

    return this.api.get(url);
  }

  public getAllActive(): Observable<any> {
    return this.api.get(`${this.baseUrl}/active`);
  }

  public getFeatured(): Observable<any> {
    return this.api.get(`${this.baseUrl}/featured`);
  }

  public getFavourite(): Observable<any> {
    return this.api.get(`${this.baseUrl}/favourite`);
  }

  public getWinnings(): Observable<any> {
    return this.api.get(`${this.baseUrl}/won`);
  }

  public getBidded(): Observable<any> {
    return this.api.get(`${this.baseUrl}/bidded`);
  }

  public getFiltered(filter: FilterDTO): Observable<any> {
    return this.api.post(`${this.baseUrl}/filter`, filter);
  }

  public getSingle(id: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${id}`);
  }

  public getWinningInformation(id: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${id}/winningInformation`);
  }

  public post(auction: CreateAuctionDTO): Observable<any> {
    return this.api.post(`${this.baseUrl}`, auction);
  }

  public put(id: number, auction: CreateAuctionDTO): Observable<any> {
    return this.api.put(`${this.baseUrl}/${id}`, auction);
  }

  public delete(id: number): Observable<any> {
    return this.api.delete(`${this.baseUrl}/${id}`);
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

  public toggleFavourite(auctionId: number, isFavourite: boolean): Observable<any> {
    return this.api.post(`${this.baseUrl}/${auctionId}/favourite`, { isFavourite });
  }
}
