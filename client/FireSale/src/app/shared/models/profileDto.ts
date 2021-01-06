import { BaseDto } from 'src/app/shared/baseDto';

export interface ProfileDto extends BaseDto {
  displayName: string;
  avatar: string;
}
