import { DateSelectionModelChange } from '@angular/material/datepicker';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { ProfileDto } from 'src/app/users/models/profileDto';
import { ImageDTO } from './imageDTO';

export interface AuctionDTO {
  id: number;
  name: string;
  description: string;
  minimalBid: number;
  startDate: Date;
  endDate: Date;
  user: ProfileDto;
  categories: CategoryDTO[];
  images: ImageDTO[];
  tags: string[];
}
