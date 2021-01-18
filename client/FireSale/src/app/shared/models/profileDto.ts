import { BaseDto as BaseDTO } from 'src/app/shared/baseDto';

export interface ProfileDTO extends BaseDTO {
  displayName: string;
  avatar: string;
}
