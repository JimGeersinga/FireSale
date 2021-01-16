import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CreateAuctionDTO } from 'src/app/shared/models/createAuctionDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  public model$: Observable<ApiResponse<AuctionDTO>>;
  public canCancelAuction = false;

  private auctionId: number;

  constructor(
    private route: ActivatedRoute,
    private auctionService: AuctionService,
    private userService: UserService,
    private dialog: MatDialog
  ) { }

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
}
