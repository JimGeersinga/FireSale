import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup, ValidationErrors, AbstractControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { CustomErrorStateMatcher } from 'src/app/core/providers/CustomErrorStateMatcher';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { CreateImageDTO } from 'src/app/shared/models/createImageDto';
import { UpdateUserDto } from 'src/app/shared/models/updateUserDto';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {
  public profileUpdateForm: any;
  public id: number;
  matcher = new CustomErrorStateMatcher();
  public selectedFile: CreateImageDTO;

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
          password: [null, ],
          passwordVerify: [null, ]
        },
          { validators: this.checkPasswords }
        );
      });
    });
  }

  private checkPasswords(group: FormGroup): any {
    const pass = group.get('password').value;
    const confirmPass = group.get('passwordVerify').value;
    return !pass || pass == confirmPass ? null : { notSame: true };
  }

  private requiredIfValidator(predicate: any): ValidationErrors | null {
    return ((formControl: AbstractControl) => {
      if (!formControl.parent) {
        return null;
      }
      if (predicate()) {
        return Validators.required(formControl);
      }
      return null;
    });
  }

  public selectFile(event): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      const encodedImage = e.target.result.split('base64,')[1];
      const fileExtension = '.' + e.target.result.split(';')[0].split('/')[1];
      this.selectedFile = {
        id: null,
        path: encodedImage,
        type: fileExtension,
        sort: 0,
      }; // voorbeeld weergevan op pagina
    };
    reader.readAsDataURL(event.target.files[0]);
  }

  submitProfileUpdate(id: number, data: UpdateUserDto): void {
    if (!this.profileUpdateForm.valid) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Wijzigen gegevens', message: 'Formulier is niet correct ingevuld' } });
    }
    else {
      if (this.selectedFile) {
        data.avatar = this.selectedFile;
      }

      if (!data.password) {

        data.password = null;
      }

      this.userService.updateProfile(id, data).subscribe(_ => { console.log('test'); });

      this.dialog.open(OkDialogComponent, { data: { title: 'Wijzigen gegevens', message: 'Gegevens zijn gewijzigd' } });
    }
  }
}

