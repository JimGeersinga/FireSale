import { AddressDto } from './addressDto';

export interface RegisterDto {
    email: string,
    gender: string,
    firstName: string,
    lastName: string,
    address: AddressDto,
    dateOfBirth: Date,
    displayName: string,
    password: string,
    passwordVerify: string
}