import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let apiService: ApiService;

  const returnValue = {
    success: true,
    data: {}
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(UserService);
    apiService = TestBed.inject(ApiService);

    spyOn(apiService, 'get').and.returnValue(of(returnValue));
    spyOn(apiService, 'post').and.returnValue(of(returnValue));
    spyOn(apiService, 'put').and.returnValue(of());
    spyOn(apiService, 'patch').and.returnValue(of());
    spyOn(apiService, 'delete').and.returnValue(of());
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return register observable', () => {
    service.register({
      email: 'test@firesale.nl',
      gender: 'MALE',
      firstName: 'test',
      lastName: '',
      dateOfBirth: new Date(),
      displayName: 'Test',
      password: 'password 123',
      passwordVerify: null,
      address: {
        street: 'kerkstraat',
        houseNumber: '1',
        postalCode: '1234AB',
        city: 'Amsterdam',
        country: 'Nederland'
      },
      shippingAddress: null
    }).subscribe((response) => {
      expect(response).toEqual(returnValue);
    });
  });

  it('should return login observable', () => {
    service.login({
      email: 'test@firesale.nl',
      password: 'password 123'
    }).subscribe((response) => {
      expect(response).toEqual(returnValue);
    });
  });

  it('should return getUserProfile observable', () => {
    service.getUserProfile(1).subscribe((response) => {
      expect(response).toEqual(returnValue);
    });
  });

  it('should return getUserAuctions observable', () => {
    service.getUserAuctions(1).subscribe((response) => {
      expect(response).toEqual(returnValue);
    });
  });

  it('should return delete observable', () => {
    service.delete().subscribe((response) => {
      expect(response).toEqual(null);
    });
  });
});
