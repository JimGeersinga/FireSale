import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { LoginDto } from '../../models/loginDto';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm = this.formBuilder.group({
    email: ['', Validators.required],
    password: ['', Validators.required],
  });

  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    // get return url from route parameters or default to '/'
    this.route.queryParams.subscribe(params => {
      this.returnUrl = params.returnUrl || '/';
    });
  }

  submitLogin(data: LoginDto): void {
    if (!this.loginForm.valid) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Loginformulier', message: 'Loginformulier is niet correct ingevuld' } });
    }
    else {
      this.userService.login(data).subscribe(
        _ => this.router.navigate([this.returnUrl]),
        error => console.log(error)
      );
    }
  }
}