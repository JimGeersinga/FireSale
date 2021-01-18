import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ApiService } from 'src/app/core/services/api.service';
import { ChangepasswordDto } from '../models/ChangepasswordDto';
import { EmailaddressDto } from '../models/emailaddressDto';
import { LoginDTO } from '../models/loginDto';
import { RegisterDTO } from '../models/registerDto';
import { UpdateUserDTO } from '../models/updateUserDto';
import { UserDTO } from '../models/userDto';


@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'users';

  public currentUser$: BehaviorSubject<UserDTO | null> = new BehaviorSubject(null);
  public userIsAdmin$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  constructor(private api: ApiService) {
    this.loadCurrentUser();
  }

  public register(registerDto: RegisterDTO): Observable<any> {
    return this.api.post(`${this.baseUrl}`, registerDto);
  }

  public login(loginDto: LoginDTO): Observable<any> {
    return this.api.post(`${this.baseUrl}/authenticate`, loginDto).pipe(
      tap({
        next: (response) => {
          const user = response.data;
          if (response.success && response.data) {
            user.authData = window.btoa(
              `${loginDto.email}:${loginDto.password}`
            );
            localStorage.setItem('currentUser', JSON.stringify(user));
            this.loadCurrentUser();
          } else if (!response.success) {
            console.log(response.errorCode, response.errorMessage);
          }
          return user;
        },
        error: (err) => console.log(err),
      })
    );
  }

  public logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUser$.next(null);
    this.userIsAdmin$.next(false);
  }


  public updateProfile(id: number, updateUserDto: UpdateUserDTO): Observable<any> {
    return this.api.patch(`${this.baseUrl}/${id}`, updateUserDto);
  }

  public getUserProfile(id: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${id}`);
  }

  public getUserAuctions(id: number): Observable<any> {
    return this.api.get(`${this.baseUrl}/${id}/auctions`);
  }


  public requestPassword(emailaddress: EmailaddressDto): Observable<any> {
    return this.api.post(`${this.baseUrl}` + '/forgotpassword', emailaddress);
  }

  public changePassword(
    newPasswordAndToken: ChangepasswordDto
  ): Observable<any> {
    return this.api.post(
      `${this.baseUrl}` + '/changepassword',
      newPasswordAndToken
    );
  }
  
  private loadCurrentUser(): void {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser) {
      this.currentUser$.next(currentUser);
      this.userIsAdmin$.next(currentUser.role === 'ADMIN');
    }
  }
}
