import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigData } from './config-data';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private config: ConfigData = {
    apiRoot: '',
    webSocketRoot: ''
  };

  constructor(private http: HttpClient) { }

  async load(): Promise<void> {
    const data = await this.http
      .get<ConfigData>('assets/config.json')
      .toPromise();
    this.config = data;
  }

  get(): ConfigData {
    return this.config;
  }
}
