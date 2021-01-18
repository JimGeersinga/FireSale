import { BaseDto as BaseDTO } from 'src/app/shared/baseDto';
import { AddressDTO } from './addressDto';

export interface UserDTO extends BaseDTO {
  email: string;
  gender: string;
  firstName: string;
  lastName: string;
  address: AddressDTO;
  shippingAddress: AddressDTO;
  dateOfBirth: Date;
  displayName: string;
  password: string;
  passwordVerify: string;
  authData: string;
  avatar: string;
  role: string;
}
