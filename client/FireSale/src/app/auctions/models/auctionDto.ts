import { DateSelectionModelChange } from '@angular/material/datepicker';

export interface AuctionDto {
    auctionName: string;
    auctionDescription: string;
    minimalBid: string,
    auctionStartdate: Date;
    auctionEnddate: Date;
}