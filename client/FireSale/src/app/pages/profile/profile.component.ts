import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { YesNoDialogComponent } from 'src/app/shared/components/yes-no-dialog/yes-no-dialog.component';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { ProfileDTO } from 'src/app/shared/models/profileDto';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public user$: Observable<ApiResponse<ProfileDTO>>;
  public isCurrentUser = false;
  public id: number;
  public usersAuctions: AuctionDTO[];

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private snackbar: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.userService.getUserAuctions(this.id).subscribe(response => this.usersAuctions = response.data);
      this.user$ = this.userService.getUserProfile(params.id);
      this.userService.currentUser$.subscribe(user => {
        this.isCurrentUser = user?.id === +params.id;
      });
    });
  }

  public remove(): void{
    const dialog = this.dialog.open(YesNoDialogComponent, { data: { title: 'Account verwijderen', message: 'Weet u zeker dat u uw account wilt verwijderen?' } });
    dialog.afterClosed().subscribe((result) => {
      this.userService.delete().subscribe();
      this.router.navigate(['/login']);
      this.snackbar.open('U bent uitgelogd');
    });
  }
}
