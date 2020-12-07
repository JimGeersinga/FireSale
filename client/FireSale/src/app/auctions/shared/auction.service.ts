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
    let response = this.api.get(`${this.baseUrl}`);
    console.log(response);
    return response;
  }

  public getSingle(id: number): Observable<any>{
    let response = this.api.get(`${this.baseUrl}/${id}`);
    console.log(response);
    return response;
  }

  public post(auction:AuctionDTO)
  {
    return this.api.post(`${this.baseUrl}`, auction);
  }

}
