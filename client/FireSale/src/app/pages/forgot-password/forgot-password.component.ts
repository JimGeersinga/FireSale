import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { EmailaddressDTO } from 'src/app/shared/models/emailaddressDto';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit {
  forgotpasswordForm = this.formBuilder.group({
    emailaddress: ['', Validators.required],
  });

  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.returnUrl = params.returnUrl || '/';
    });
  }

  submitForgotpasswordform(data: EmailaddressDTO): void {
    if (!this.forgotpasswordForm.valid) {
      this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Wachtwoord vergeten',
          message: 'U heeft geen geldig e-mailadres opgegeven',
        },
      });
    } else {
      this.userService.requestPassword(data).subscribe();
      this.dialog.open(OkDialogComponent, {
        data: {
          title: 'Wachtwoord vergeten',
          message:
            'Als u een geldig e-mailadres opgegeven heeft dan is er nu een reset mail verzonden.',
        },
      });
    }
  }
}
