import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { ProfileComponent } from './pages/profile/profile.component';
import { HomeComponent } from './pages/home/home.component';
import { CategoryDetailComponent } from './pages/category-detail/category-detail.component';
import { CategoryEditComponent } from './pages/category-edit/category-edit.component';
import { AdminGuard } from './core/guards/admin.guard';
import { AuctionsComponent } from './pages/auctions/auctions.component';
import { AuctionEditComponent } from './pages/auction-edit/auction-edit.component';
import { AuctionDetailComponent } from './pages/auction-detail/auction-detail.component';
import { ProfileEditComponent } from './pages/profile-edit/profile-edit.component';
import { CategoriesComponent } from './pages/categories/categories.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},

  { path: 'auctions', component: AuctionsComponent},
  { path: 'auctions/edit', component: AuctionEditComponent, canActivate: [AuthGuard]  },
  { path: 'auctions/details/:id', component: AuctionDetailComponent },

  { path: 'categories', component: CategoriesComponent, canActivate: [AdminGuard]},
  { path: 'categories/edit', component: CategoryEditComponent, canActivate: [AdminGuard]  },
  { path: 'categories/details/:id', component: CategoryDetailComponent, canActivate: [AdminGuard] },

  { path: 'profile/:id', component: ProfileComponent },
  { path: 'profile/:id/edit', component: ProfileEditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
