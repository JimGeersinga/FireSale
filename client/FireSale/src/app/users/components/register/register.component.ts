import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { RegisterDto } from '../../models/registerDto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm = this.formBuilder.group(
    {
      email: ['', Validators.required],
      gender: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      address: this.formBuilder.group({
        street: ['', Validators.required],
        houseNumber: ['', Validators.required],
        zipCode: ['', Validators.required],
        city: ['', Validators.required]
      }),
      displayName: ['', Validators.required],
      password: ['', Validators.required],
      passwordVerify: ['', Validators.required]
    }
  );

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  submitRegistration(data: RegisterDto): void {
    if (!this.registerForm.valid) {
      this.dialog.open(OkDialogComponent, {data: {title: "Registratieformulier", message: "Registratieformulier is niet correct ingevuld"}});
    }
    else {
      this.dialog.open(OkDialogComponent, {data: {title: "Registratieformulier", message: "Registratieformulier is verzonden"}});
    }
  }
}
