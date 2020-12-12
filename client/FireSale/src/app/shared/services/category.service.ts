import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl = 'categories';

  constructor(private api: ApiService) { }

  public get(): Observable<any> { 
    return this.api.get(`${this.baseUrl}`);
  }
}
