import { TagDTO } from 'src/app/shared/models/tagDto';
import { CreateImageDTO } from './createImageDto';

export interface CreateAuctionDTO {
  id?: number;
  name?: string;
  description?: string;
  isFeatured?: boolean;
  minimalBid?: number;
  status?: string;
  startDate?: Date;
  endDate?: Date;
  categories?: number[];
  images?: CreateImageDTO[];
  tags?: TagDTO[];
}
