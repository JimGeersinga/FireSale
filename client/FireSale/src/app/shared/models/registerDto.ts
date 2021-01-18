import { AddressDTO } from './addressDto';

export interface RegisterDTO {
    email: string;
    gender: string;
    firstName: string;
    lastName: string;
    dateOfBirth: Date;
    displayName: string;
    password: string;
    passwordVerify: string;
    address: AddressDTO;
    shippingAddress: AddressDTO;
}
