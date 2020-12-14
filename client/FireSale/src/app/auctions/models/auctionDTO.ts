import { DateSelectionModelChange } from '@angular/material/datepicker';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { ImageDTO } from './imageDTO';

export interface AuctionDTO {
  id: number;
  name: string;
  description: string;
  minimalBid: number;
  startDate: Date;
  endDate: Date;
  categories: CategoryDTO[];
  images: ImageDTO[];
  tags: string[];
}
