import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CreateAuctionDTO } from 'src/app/shared/models/createAuctionDto';
import { ProfileDto } from 'src/app/shared/models/profileDto';
import { UserDto } from 'src/app/shared/models/userDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  public model$: Observable<ApiResponse<AuctionDTO>>;
  public isAdmin = false;
  public user$: Observable<ApiResponse<ProfileDto>>;
  public canCancelAuction = false;
  private auctionId: number;

  constructor(
    private route: ActivatedRoute,
    private auctionService: AuctionService,
    private userService: UserService,
    private dialog: MatDialog
  ) {
      this.userService.currentUser$.subscribe(item => {
      this.isAdmin = item && item.role === 'ADMIN';
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.auctionId = params.id;
      this.loadAuction();
    });
  }

  public loadAuction(): void {
    this.model$ = this.auctionService.getSingle(this.auctionId);
    this.model$.subscribe(response => {
      const auction = response.data;

      this.userService.currentUser$.subscribe(currentUser => {
        if (!currentUser) {
          this.canCancelAuction = false;
          return;
        }
        const currentUserIsAdmin = currentUser.role === 'ADMIN';
        this.canCancelAuction = (auction.status === 'READY' && ((currentUser.id === auction.user.id && (auction.startDate > new Date())) || currentUserIsAdmin));
      });
    });
  }

  cancelAuction(data: CreateAuctionDTO): void {
    data.status = 'CANCELLED';
    this.auctionService.patch(this.auctionId, data).subscribe(() => {
      this.loadAuction();
    });
    this.dialog.open(OkDialogComponent, { data: { title: 'Veiling annuleren', message: 'De veiling is geannuleerd' } });
  }

  markAsFeatured(data: CreateAuctionDTO): void {
    data.isFeatured = true;
    this.auctionService.patch(this.auctionId, data).subscribe(x => {console.log('test')});
    this.dialog.open(OkDialogComponent, { data: { title: 'Feature', message: 'De veiling is aangemerkt als featured.' } });
  }
}
