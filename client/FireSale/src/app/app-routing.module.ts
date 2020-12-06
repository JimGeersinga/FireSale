import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './users/components/register/register.component';
import { AuctionListComponent } from './auctions/components/auction-list/auction-list.component';
import { LoginComponent } from './users/components/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { NewAuctionComponent } from './auctions/components/new-auction/new-auction.component';
import { AuctionDetailComponent } from './auctions/components/auction-detail/auction-detail.component';

const routes: Routes = [
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: '', component: AuctionListComponent},
  { path: 'auctions/create', component: NewAuctionComponent, canActivate: [AuthGuard]  },
  { path: 'auctions/details', component: AuctionDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
