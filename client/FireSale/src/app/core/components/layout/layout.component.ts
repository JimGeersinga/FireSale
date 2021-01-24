import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserDTO } from 'src/app/shared/models/userDto';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
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

  public logout(): void {
    this.userService.logout();
    this.router.navigate(['/login']);
    this.snackbar.open('U bent uitgelogd');
  }
}
