import { DateSelectionModelChange } from '@angular/material/datepicker';

export interface AuctionDTO {
  id: number;
  name: string;
  description: string;
  minimalBid: number;
  startDate: Date;
  endDate: Date;
}
