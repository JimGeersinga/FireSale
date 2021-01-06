import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { ProfileDto } from 'src/app/shared/models/profileDto';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public user$: Observable<ApiResponse<ProfileDto>>;
  public isCurrentUser = false;
  public id: number;
  public usersAuctions: AuctionDTO[];

  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.userService.getUserAuctions(this.id).subscribe(response => this.usersAuctions = response.data);
      console.log(this.usersAuctions); // remove this later
      this.user$ = this.userService.getUserProfile(params.id);
      this.userService.currentUser$.subscribe(user => {
        this.isCurrentUser = user.id === +params.id;
      });
    });
  }
}
