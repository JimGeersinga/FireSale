import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserDto } from 'src/app/users/models/userDto';
import { UserService } from 'src/app/users/shared/user.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnDestroy, AfterViewInit, OnInit {
  public currentUser$: Observable<UserDto>;


  constructor(
    private userService: UserService,
    private router: Router,
    private snackbar: MatSnackBar) {
    this.currentUser$ = this.userService.currentUser$;
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
  }

  public logout(): void {
    this.userService.logout();
    this.router.navigate(['/login']);
    this.snackbar.open('U bent uitgelogd');
  }
}
