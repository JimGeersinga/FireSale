import { BaseDto } from "src/app/shared/baseDto";

export interface ImageDTO extends BaseDto{
  path: string;
  type: string;
  sort: number;
}
