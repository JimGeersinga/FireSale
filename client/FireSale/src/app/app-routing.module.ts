import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { ProfileComponent } from './pages/profile/profile.component';
import { CategoryEditComponent } from './pages/category-edit/category-edit.component';
import { AdminGuard } from './core/guards/admin.guard';
import { AuctionsComponent } from './pages/auctions/auctions.component';
import { AuctionEditComponent } from './pages/auction-edit/auction-edit.component';
import { AuctionDetailComponent } from './pages/auction-detail/auction-detail.component';
import { ProfileEditComponent } from './pages/profile-edit/profile-edit.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { AuctionsSearchComponent } from './pages/auctions-search/auctions-search.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { AuctionsFilteredComponent } from './pages/auctions-filtered/auctions-filtered.component';


const routes: Routes = [
  { path: '', redirectTo: '/auctions', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},

  { path: 'auctions', component: AuctionsComponent},
  { path: 'auctions/my-bids', component: AuctionsFilteredComponent, data: {routeName: 'myBids'}},
  { path: 'auctions/my-winnings', component: AuctionsFilteredComponent, data: {routeName: 'myWinnings'}},
  { path: 'auctions/my-favourites', component: AuctionsFilteredComponent, data: {routeName: 'myFavourites'}},
  { path: 'auctions/my-auctions', component: AuctionsFilteredComponent, data: {routeName: 'myAuctions'}},
  { path: 'auctions/search', component: AuctionsSearchComponent },
  { path: 'auctions/create', component: AuctionEditComponent, canActivate: [AuthGuard]  },
  { path: 'auctions/:id', component: AuctionDetailComponent },
  { path: 'auctions/:id/edit', component: AuctionEditComponent, canActivate: [AuthGuard]  },

  { path: 'categories', component: CategoriesComponent, canActivate: [AdminGuard]},
  { path: 'categories/create', component: CategoryEditComponent, canActivate: [AdminGuard]  },

  { path: 'profile/:id', component: ProfileComponent },
  { path: 'profile/:id/edit', component: ProfileEditComponent },

  { path: 'users/forgotpassword', component: ForgotPasswordComponent },
  { path: 'users/changepassword/:token', component: ChangePasswordComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
