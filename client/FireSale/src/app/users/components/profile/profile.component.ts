import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ProfileDto } from '../../models/profileDto';
import { UserDto } from '../../models/userDto';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public user$: Observable<ProfileDto>;
  public isCurrentUser: boolean = true;
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
        this.isCurrentUser = user.id == params.id
      });
    });
  }
}
