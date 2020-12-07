import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { UserDto } from '../../models/userDto';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-profile-update',
  templateUrl: './profile-update.component.html',
  styleUrls: ['./profile-update.component.scss']
})
export class ProfileUpdateComponent implements OnInit {
  public profileUpdateForm: any;
  public id: number;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private userService: UserService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.userService.currentUser$.subscribe(user => {
        this.profileUpdateForm = this.formBuilder.group({
          email: [user?.email, Validators.email],
          gender: [user?.gender],
          firstName: [user?.firstName],
          lastName: [user?.lastName],
          dateOfBirth: [user?.dateOfBirth],
          address: this.formBuilder.group({
            street: [user?.address?.street],
            houseNumber: [user?.address?.houseNumber],
            postalCode: [user?.address?.postalCode],
            city: [user?.address?.city],
            country: [user?.shippingAddress?.country]
          }),
          shippingAddress: this.formBuilder.group({
            street: [user?.shippingAddress?.street],
            houseNumber: [user?.shippingAddress?.houseNumber],
            postalCode: [user?.shippingAddress?.postalCode],
            city: [user?.shippingAddress?.city],
            country: [user?.shippingAddress?.country]
          }),
          displayName: [user.displayName],
          password: [''],
          passwordVerify: ['']
        });
      });
    });
  }

  submitProfileUpdate(id: number, data: UserDto): void {
    if (!this.profileUpdateForm.valid) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Wijzigen gegevens', message: 'Formulier is niet correct ingevuld' } });
    }
    else {
      this.userService.updateProfile(id, data);

      this.dialog.open(OkDialogComponent, { data: { title: 'Wijzigen gegevens', message: 'Gegevens zijn gewijzigd' } });
    }
  }
}

