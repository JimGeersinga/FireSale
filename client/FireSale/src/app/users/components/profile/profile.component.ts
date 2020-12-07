import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { ProfileDto } from '../../models/profileDto';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public user$: Observable<ApiResponse<ProfileDto>>;
  public isCurrentUser = false;
  public id: number;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.user$ = this.userService.getUserProfile(params.id);
      this.userService.currentUser$.subscribe(user => {
        this.isCurrentUser = user.id === +params.id;
      });
    });
  }
}
