import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ApiService } from 'src/app/core/services/api.service';
import { LoginDto } from '../models/loginDto';
import { RegisterDto } from '../models/registerDto';
import { UpdateUserDto } from '../models/updateUserDto';
import { UserDto } from '../models/userDto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'users';

  public currentUser$: BehaviorSubject<UserDto | null> = new BehaviorSubject(null);

  constructor(private api: ApiService) {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser) {
      this.currentUser$.next(currentUser);
    }
  }

  public register(registerDto: RegisterDto): Observable<any> {
    return this.api.post(`${this.baseUrl}`, registerDto);
  }

  public login(loginDto: LoginDto): Observable<any> {
    return this.api.post(`${this.baseUrl}/authenticate`, loginDto).pipe(
      tap({
        next: response => {
          const user = response.data;
          if (response.success && response.data) {
            user.authData = window.btoa(`${loginDto.email}:${loginDto.password}`);
            localStorage.setItem('currentUser', JSON.stringify(user));
            this.currentUser$.next(user);
          } else if (!response.success) {
            console.log(response.errorCode, response.errorMessage);
          }
          return user;
        },
        error: err => console.log(err)
      }));
  }

  public logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUser$.next(null);
  }

  public updateProfile(id: number, updateUserDto: UpdateUserDto): Observable<any> {
    console.log(updateUserDto);
    return this.api.patch(`${this.baseUrl}/${id}`, updateUserDto);
  }

  public getUserProfile(id: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${id}`);
  }

  public getUserAuctions(id: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${id}/auctions`);
  }
}
