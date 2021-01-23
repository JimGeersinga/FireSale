export interface WebSocketAuctionMessage<T> {
  responseType: AuctionMessageResponseType;
  data: T;
  userId: number;
  messageTime: Date;
}
export enum AuctionMessageResponseType {
  BID_PLACED = 'BID_PLACED',
  UPDATED = 'UPDATED'
}
