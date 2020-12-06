import { DateSelectionModelChange } from '@angular/material/datepicker';

export interface AuctionDTO {
    name: string;
    description: string;
    minimalBid: number,
    startDate: Date;
    endDate: Date;
}