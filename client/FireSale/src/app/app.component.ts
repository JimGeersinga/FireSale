import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserDTO } from './shared/models/userDto';
import { UserService } from './shared/services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'FireSale';

  public currentUser$: Observable<UserDTO>;

  constructor(
    private userService: UserService,
    private router: Router,
    private snackbar: MatSnackBar,
    ) {
    this.currentUser$ = this.userService.currentUser$;
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/']);
    this.snackbar.open('You have been logged out!');
  }
}
