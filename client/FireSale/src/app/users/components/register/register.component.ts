import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomErrorStateMatcher } from 'src/app/core/providers/CustomErrorStateMatcher';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { RegisterDto } from '../../models/registerDto';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  matcher = new CustomErrorStateMatcher();

  registerForm = this.formBuilder.group(
    {
      email: ['', [Validators.required, Validators.email]],
      gender: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      displayName: ['', Validators.required],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)]],
      passwordVerify: ['', Validators.required],
      address: this.formBuilder.group({
        street: ['', Validators.required],
        houseNumber: ['', Validators.required],
        postalCode: ['', [Validators.required, Validators.pattern(/^[0-9]{4}(\s?)[a-zA-Z]{2}$/)]],
        city: ['', Validators.required],
        country: ['', Validators.required]
      }),
      shippingAddress: this.formBuilder.group({
        supplyShippingAddress: [false],
        street: ['', this.requiredIfValidator(() => this.registerForm.get('supplyShippingAddress')?.value)],
        houseNumber: ['', this.requiredIfValidator(() => this.registerForm.get('supplyShippingAddress')?.value)],
        postalCode: ['', [this.requiredIfValidator(() => this.registerForm.get('supplyShippingAddress')?.value), Validators.pattern(/^[0-9]{4}(\s?)[a-zA-Z]{2}$/)]],
        city: ['', this.requiredIfValidator(() => this.registerForm.get('supplyShippingAddress')?.value)],
        country: ['', this.requiredIfValidator(() => this.registerForm.get('supplyShippingAddress')?.value)]
      }),
    },
    { validators: this.checkPasswords }
  );

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  private checkPasswords(group: FormGroup): any {
    const pass = group.get('password').value;
    const confirmPass = group.get('passwordVerify').value;

    return pass === confirmPass ? null : { notSame: true };
  }

  private requiredIfValidator(predicate: any): ValidationErrors | null{
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

  public submitRegistration(data: RegisterDto): void {
    if (!this.registerForm.valid) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Registratieformulier', message: 'Registratieformulier is niet correct ingevuld' } });
    }
    else {
      if (!this.registerForm.value.shippingAddress.supplyShippingAddress) {
        data.shippingAddress = null;
      }
      this.userService.register(data).subscribe((response) => {
        const dialogRef = this.dialog.open(OkDialogComponent, { data: { title: 'Registratieformulier', message: 'U bent succesvol geregistreerd, u kunt nu inloggen' } });
        dialogRef.afterClosed().subscribe(_ => this.router.navigate(['/login']));
      });
    }
  }
}
