import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDto } from 'src/app/shared/models/userDto';
import { UserService } from 'src/app/shared/services/user.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {
  public currentUser$: Observable<UserDto>;
  public isAdmin = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private snackbar: MatSnackBar) {
    this.currentUser$ = this.userService.currentUser$;
    this.currentUser$.subscribe(item => {
      this.isAdmin = item && item.role === 'ADMIN';
    });
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/']);
    this.snackbar.open('You have been logged out!');
  }

}
