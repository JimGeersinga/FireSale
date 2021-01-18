import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomErrorStateMatcher } from 'src/app/core/providers/CustomErrorStateMatcher';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { UserService } from 'src/app/shared/services/user.service';
import { ChangepasswordDto } from 'src/app/shared/models/ChangepasswordDto';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  public changepasswordForm: any;
  public token: string;
  matcher = new CustomErrorStateMatcher();

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.token = params.token;
      this.changepasswordForm = this.formBuilder.group(
        {
          password: [
            '',
            [
              Validators.required,
              Validators.pattern(
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/
              ),
            ],
          ],
          passwordVerify: [null],
          token: this.token,
        },
        { validators: this.checkPasswords }
      );
    });
  }

  private checkPasswords(group: FormGroup): any {
    const pass = group.get('password').value;
    const confirmPass = group.get('passwordVerify').value;
    return !pass || pass == confirmPass ? null : { notSame: true };
  }

  submitChangepasswordform(formdata: ChangepasswordDto): void {
    if (!this.changepasswordForm.valid) {
      this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Wijzigen gegevens',
          message: 'Formulier is niet correct ingevuld',
        },
      });
    } else {
      this.userService
        .changePassword({ password: formdata.password, token: formdata.token })
        .subscribe(() => {
          const dialogRef = this.dialog.open(OkDialogComponent, {
            data: {
              title: 'Wijzigen gegevens',
              message: 'Gegevens zijn gewijzigd',
            },
          });
          dialogRef
            .afterClosed()
            .subscribe(() => this.router.navigate(['/login']));
        });
    }
  }
}
