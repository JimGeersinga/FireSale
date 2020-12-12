import { DateSelectionModelChange } from '@angular/material/datepicker';

export interface CreateAuctionDTO {
  id: number;
  name: string;
  description: string;
  minimalBid: number;
  startDate: Date;
  endDate: Date;
  categories: number[];
}
