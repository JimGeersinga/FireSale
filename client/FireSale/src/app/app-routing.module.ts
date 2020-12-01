import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './users/components/register/register.component';
import { AuctionListComponent } from './auctions/auction-list/auction-list.component';
import { LoginComponent } from './users/components/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: '', component: AuctionListComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
