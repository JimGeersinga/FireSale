import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private config: any;

  constructor(private http: HttpClient) {}

  async load(): Promise<void> {
    const data = await this.http
      .get('assets/config.json')
      .toPromise();
    this.config = data;
  }

  get(): any {
    return this.config;
  }
}
