import { AddressDTO } from './addressDto';

export interface WinningInformationDTO {
  owner: {
    name: string;
    email: string;
  };
  winner: {
    name: string;
    email: string;
    address: AddressDTO;
  };
}
