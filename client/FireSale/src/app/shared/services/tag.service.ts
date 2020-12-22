import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from 'src/app/core/services/api.service';
import { TagDto } from '../models/tagDto';

interface DataObject {
  id: number;
  modified: string;
  created: string;
  name: string;
}

interface GetTagResponse{
  success: boolean;
  data: DataObject[];
}

@Injectable({
  providedIn: 'root'
})

export class TagService {
  
  private baseUrl = 'tags';

  constructor(private api: ApiService) { }

  public getAllTags(): Observable<string[]> { 
    return this.api.get(`${this.baseUrl}`);
  }

  public searchTagsByName(searchterm : string) : Observable<string[]> {
    const param = new URLSearchParams();
    param.append("searchterm",searchterm);
    return this.api.get(`${this.baseUrl}?${param}`).pipe(
      map((data : GetTagResponse) => data.data.map(d => d.name))
    )
  }

  public put(tagDto: TagDto): Observable<any> {
    return this.api.put(`${this.baseUrl}`, tagDto.name);
  }
}

