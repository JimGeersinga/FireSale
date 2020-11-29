import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ApiService } from 'src/app/core/services/api.service';
import { LoginDto } from '../models/loginDto';
import { RegisterDto } from '../models/registerDto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'users';

  constructor(private api: ApiService) {}

  public register(registerDto: RegisterDto): Observable<any> {
    return this.api.post(`${this.baseUrl}`, registerDto);
  }

  public login(loginDto: LoginDto): Observable<any> {
    return this.api.get(`${this.baseUrl}/authenticate`, loginDto).pipe(
      tap({
        next: user => {
          if (user) {
            user.authData = window.btoa(`${loginDto.email}:${loginDto.password}`);
            localStorage.setItem('currentUser', JSON.stringify(user));
          }
          return user;
        },
        error: err => console.log(err)
      }));
  }

  public logout(): void {
    localStorage.removeItem('currentUser');
  }
}
