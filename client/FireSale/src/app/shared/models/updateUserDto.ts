import { CreateImageDTO } from 'src/app/shared/models/createImageDto';
import { BaseDto } from 'src/app/shared/baseDto';
import { AddressDto } from './addressDto';

export interface UpdateUserDto extends BaseDto {
    email: string;
    gender: string;
    firstName: string;
    lastName: string;
    address: AddressDto;
    shippingAddress: AddressDto;
    dateOfBirth: Date;
    displayName: string;
    password: string;
    passwordVerify: string;
    authData: string;
    avatar: CreateImageDTO;
}
