import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { TagDto } from 'src/app/shared/models/tagDto';
import { ProfileDto } from 'src/app/shared/models/profileDto';
import { ImageDTO } from './imageDTO';

export interface AuctionDTO {
  id: number;
  name: string;
  description: string;
  isFeatured: boolean;
  minimalBid: number;
  startDate: Date;
  endDate: Date;
  status: string;
  user: ProfileDto;
  categories: CategoryDTO[];
  images: ImageDTO[];
  tags: TagDto[];
}
