import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register/register.component';
import { SharedModule } from './shared/shared.module';

const routes: Routes = [
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    SharedModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
