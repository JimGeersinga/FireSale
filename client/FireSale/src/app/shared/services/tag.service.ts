import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { TagDTO } from '../models/tagDto';

@Injectable({
  providedIn: 'root'
})

export class TagService {

  private baseUrl = 'tags';

  constructor(private api: ApiService) { }

  public getAllTags(): Observable<any> {
    return this.api.get(`${this.baseUrl}`);
  }

  public searchTagsByName(searchterm: string): Observable<ApiResponse<TagDTO[]>> {
    const param = new URLSearchParams();
    param.append('searchterm', searchterm);
    return this.api.get(`${this.baseUrl}?${param}`);
  }

  public put(tagDto: TagDTO): Observable<any> {
    return this.api.put(`${this.baseUrl}`, tagDto.name);
  }
}

