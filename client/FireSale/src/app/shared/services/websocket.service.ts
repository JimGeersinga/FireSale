import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { WebSocketAuctionMessage } from 'src/app/shared/models/webSocketAuctionMessage';
import { ConfigService } from '../../core/services/config.service';

declare var SockJS;
declare var Stomp;

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: any;

  private isConnected$ = new BehaviorSubject<boolean>(false);

  public get webSocketRoot(): string {
    return this.config.get().webSocketRoot;
  }

  constructor(private config: ConfigService) {
    this.initializeConnection();
  }

  public listenForAuctionUpdate<T>(auctionId: number): Observable<WebSocketAuctionMessage<T>> {
    return new Observable((observer) => {
      this.isConnected$.subscribe((connected) => {
        if (connected) {
          this.stompClient.subscribe(`/rt-auction/updates/${auctionId}`, (response: any) => {
            const message: WebSocketAuctionMessage<T> = JSON.parse(response.body);
            observer.next(message);
          });
        }
      });
    });
  }

  private initializeConnection(): void {
    console.log('Initializing websocket connection');
    const ws = new SockJS(this.webSocketRoot);
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    ws.onclose = () => this.connect(1);
    this.connect(1);
  }

  private connect(tryCount: number): void {
    this.stompClient.connect(
      {},
      () => {
        console.log('WebSocketService -> connected');
        this.isConnected$.next(true);
      } ,
      (message: any) => {
        this.isConnected$.next(false);
        console.log('WebSocketService -> reconnect, try ' + tryCount, message);
        setTimeout(() => {
          this.connect(++tryCount);
        }, 1000);
      });
  }
}
