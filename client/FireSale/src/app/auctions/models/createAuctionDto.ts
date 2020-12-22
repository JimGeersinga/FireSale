import { TagDto } from 'src/app/shared/models/tagDto';
import { CreateImageDTO } from './createImageDto';

export interface CreateAuctionDTO {
  id: number;
  name: string;
  description: string;
  minimalBid: number;
  startDate: Date;
  endDate: Date;
  categories: number[];
  images: CreateImageDTO[];
  tags: TagDto[];
}
