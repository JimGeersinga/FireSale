import { BaseDto as BaseDTO } from 'src/app/shared/baseDto';

export interface ImageDTO extends BaseDTO {
  path: string;
  type: string;
  sort: number;
}
