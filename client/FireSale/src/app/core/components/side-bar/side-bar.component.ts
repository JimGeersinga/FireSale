import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from 'src/app/shared/models/userDto';
import { UserService } from 'src/app/shared/services/user.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {
  public currentUser$: Observable<UserDTO>;
  public isAdmin$: Observable<boolean>;

  constructor(
    private userService: UserService,
    private router: Router,
    private snackbar: MatSnackBar) {
    this.currentUser$ = this.userService.currentUser$;
    this.isAdmin$ = this.userService.userIsAdmin$;
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/']);
    this.snackbar.open('You have been logged out!');
  }

}
