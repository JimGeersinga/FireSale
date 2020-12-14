import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { ConfigService } from './config.service';
import { RequestParameters } from './request-parameters';
import { HttpClient, HttpParams } from '@angular/common/http';
import { RequestOptions } from './request-options';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  public get apiRoot(): string {
    return this.config.get().apiRoot;
  }
  constructor(private http: HttpClient, private config: ConfigService) {}

  public get<TResponse>(endpoint: string, params?: RequestParameters, reqOpts?: RequestOptions): Observable<TResponse> {
    if (!reqOpts) {
      reqOpts = {
        params: new HttpParams()
      };
    }

    // Support easy query params for GET requests
    if (params) {
      reqOpts.params = new HttpParams();
      for (const k in params) {
        if (params.hasOwnProperty(k)) {
          reqOpts.params = reqOpts.params.set(k, params[k]);
        }
      }
    }

    const path = `${this.config.get().apiRoot}/${endpoint}`;
    console.log('[GET]', path);
    return this.http.get<TResponse>(path, reqOpts);
  }

  public getFile(endpoint: string): any {
    return this.http.get(`${this.config.get().apiRoot}/${endpoint}`, {
      responseType: 'blob'
    });
  }

  public post<TResponse>(endpoint: string, body: any, reqOpts?: RequestOptions): Observable<TResponse> {
    const path = `${this.config.get().apiRoot}/${endpoint}`;
    console.log('[POST]', path, body);
    return this.http.post<TResponse>(path, body, reqOpts);
  }

  public postFile(endpoint: string, body: any): any {
    return this.http.post(`${this.config.get().apiRoot}/${endpoint}`, body, {
      responseType: 'blob'
    });
  }

  public put<TResponse>(endpoint: string, body: any, reqOpts?: RequestOptions): Observable<TResponse> {
    const path = `${this.config.get().apiRoot}/${endpoint}`;
    console.log('[PUT]', path, body);
    return this.http.put<TResponse>(path, body, reqOpts);
  }

  public delete<TResponse>(endpoint: string, reqOpts?: RequestOptions): Observable<TResponse> {
    const path = `${this.config.get().apiRoot}/${endpoint}`;
    console.log('[DELETE]', path);
    return this.http.delete<TResponse>(path, reqOpts);
  }

  public patch<TResponse>(endpoint: string, body: any, reqOpts?: RequestOptions): Observable<TResponse> {
    const path = `${this.config.get().apiRoot}/${endpoint}`;
    console.log('[PATCH]', path, body);
    return this.http.patch<TResponse>(path, body, reqOpts);
  }
}
