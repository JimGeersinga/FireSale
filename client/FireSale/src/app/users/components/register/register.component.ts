import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
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
  ){}
  
  ngOnInit(): void {
  }

  submitRegistration(data: RegisterDto) : void {
    console.log(data);
  }

}
