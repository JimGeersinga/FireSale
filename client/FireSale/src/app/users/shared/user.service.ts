import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ApiService } from 'src/app/core/services/api.service';
import { LoginDto } from '../models/loginDto';
import { RegisterDto } from '../models/registerDto';
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
        next: user => {
          if (user.data) {
            user.data.authData = window.btoa(`${loginDto.email}:${loginDto.password}`);
            localStorage.setItem('currentUser', JSON.stringify(user.data));
            this.currentUser$.next(user.data);
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
}
