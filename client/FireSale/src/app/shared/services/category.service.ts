import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';
import { CategoryDTO } from '../models/categoryDto';
import { CategoryUpsertDTO } from '../models/categoryUpsertDto';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private baseUrl = 'categories';
  constructor(private api: ApiService) { }

  public get(): Observable<any> {
    return this.api.get(`${this.baseUrl}`);
  }

  public getArchived(): Observable<any> {
    return this.api.get(`${this.baseUrl}/archived`);
  }

  public put(dto: CategoryUpsertDTO ): Observable<CategoryDTO> {
    return this.api.put(`${this.baseUrl}/${dto.id}`, dto);
  }
  public post(dto: CategoryUpsertDTO ): Observable<CategoryDTO> {
    return this.api.post(`${this.baseUrl}`, dto);
  }

}
