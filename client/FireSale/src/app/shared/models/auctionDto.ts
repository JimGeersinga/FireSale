import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { TagDTO } from 'src/app/shared/models/tagDto';
import { ProfileDTO } from 'src/app/shared/models/profileDto';
import { ImageDTO } from './imageDto';
import { BidDTO } from './bidDto';

export interface AuctionDTO {
  id: number;
  name: string;
  description: string;
  isFeatured: boolean;
  isFavourite: boolean;
  minimalBid: number;
  finalBid: BidDTO;
  startDate: Date;
  endDate: Date;
  status: string;
  user: ProfileDTO;
  categories: CategoryDTO[];
  images: ImageDTO[];
  tags: TagDTO[];
  bids: BidDTO[];
}
