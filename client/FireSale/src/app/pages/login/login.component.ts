import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { LoginDTO } from 'src/app/shared/models/loginDto';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm = this.formBuilder.group({
    email: ['admin@firesale.nl', Validators.required],
    password: ['admin', Validators.required],
  });

  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
    // get return url from route parameters or default to '/'
    this.route.queryParams.subscribe(params => {
      this.returnUrl = params.returnUrl || '/';
    });
  }

  submitLogin(data: LoginDTO): void {
    if (!this.loginForm.valid) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Loginformulier', message: 'Loginformulier is niet correct ingevuld' } });
    }
    else {
      this.userService.login(data).subscribe(_ => {
        this.router.navigate([this.returnUrl]);
        this.snackbar.open('U bent ingelogd.');
      });      
    }
  }
}
