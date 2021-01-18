import { AuctionState } from './enums/auction-state.enum';
import { AuctionDTO } from './models/auctionDto';

export class AuctionUtil {
  public static getState(auction: AuctionDTO): AuctionState {
    const now = new Date();
    const startDate = new Date(auction.startDate);
    const endDate = new Date(auction.endDate);

    if (auction.status === 'READY' && startDate > now) {
      return AuctionState.SCHEDULED;
    }
    else if (auction.status === 'READY' && startDate < now && endDate > now) {
      return AuctionState.RUNNING;
    }
    else if (auction.status === 'CANCELLED' || endDate < now) {
      return AuctionState.CLOSED;
    }
    return AuctionState.CLOSED;
  }
}
