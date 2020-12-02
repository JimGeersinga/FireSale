import { BaseDto } from 'src/app/shared/baseDto';
import { AddressDto } from './addressDto';

export interface UserDto extends BaseDto {
    email: string;
    gender: string;
    firstName: string;
    lastName: string;
    address: AddressDto;
    dateOfBirth: Date;
    displayName: string;
    password: string;
    passwordVerify: string;
}