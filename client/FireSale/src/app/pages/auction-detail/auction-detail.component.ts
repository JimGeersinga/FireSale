import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject, combineLatest, Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CreateAuctionDTO } from 'src/app/shared/models/createAuctionDto';
import { ProfileDto } from 'src/app/shared/models/profileDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-auction-detail',
  templateUrl: './auction-detail.component.html',
  styleUrls: ['./auction-detail.component.scss']
})
export class AuctionDetailComponent implements OnInit {
  private auctionId: number;
  private userId$: Observable<number>;
  private startDt$: Observable<Date>;
  private currentDate = new Date(Date.now());
  private isCancelled$: Observable<boolean>;
  public currentAuction;
  public model$: Observable<ApiResponse<AuctionDTO>>;
  items: Array<any> = [{ name: 'assets/images/headphone1.jfif' }, { name: 'assets/images/headphone2.jfif' }, { name: 'assets/images/headphone3.jfif' }, { name: 'assets/images/headphone4.jfif' }];
  timeLeft: number;
  public user$: Observable<ApiResponse<ProfileDto>>;
  public canCancelAuction = false;
  public canCancelAuction$: Observable<boolean>;
  public disableCancelButton$ : Observable<boolean>;
  public cancelButtonClicked$ = new BehaviorSubject<boolean>(false); 

  constructor(
    private route: ActivatedRoute,
    private auctionService: AuctionService,
    private userService: UserService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.auctionId = params.id;
      this.model$ = this.auctionService.getSingle(this.auctionId);
      this.userId$ = this.model$.pipe(map(m => m.data.user.id));
      this.startDt$ = this.model$.pipe(map(m => m.data.startDate));
      this.isCancelled$ = this.model$.pipe(map(m => m.data.status === "CANCELLED"));
      
      this.canCancelAuction$ = combineLatest([
        this.userId$,
        this.userService.currentUser$,
        this.startDt$,
        this.isCancelled$,
        this.cancelButtonClicked$])
          .pipe(
              map(([auctionUserId, user, startDt, isCancelled, cancelButtonClicked]) => { 
              const isCurrentUser = auctionUserId === user.id;
              const isAdmin = user.role === "ADMIN";
                if(isCancelled || cancelButtonClicked){
                  return false;
                }
                if(isAdmin){
                  return true;
                }
              return (isCurrentUser && (startDt > this.currentDate));
        }))

        this.disableCancelButton$ = this.canCancelAuction$.pipe(
          map(c => !c),
          startWith(false)
          );
        });
  }

  cancelAuction(data: CreateAuctionDTO): void {
    this.cancelButtonClicked$.next(true);
    data.status = "CANCELLED";
    this.auctionService.patch(this.auctionId, data).subscribe(x => {console.log('test')});
    this.dialog.open(OkDialogComponent, { data: { title: 'Veiling annuleren', message: 'De veiling is geannuleerd' } });
    }
}
