import { AddressDto } from './addressDto';

export interface RegisterDto {
    email: string;
    gender: string;
    firstName: string;
    lastName: string;
    dateOfBirth: Date;
    displayName: string;
    password: string;
    passwordVerify: string;
    address: AddressDto;
    shippingAddress: AddressDto;
}
