import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './core/components/header/header.component';
import { FooterComponent } from './core/components/footer/footer.component';
import { RegisterComponent } from './pages/register/register.component';
import { SharedModule } from './shared/shared.module';
import { SideBarComponent } from './core/components/side-bar/side-bar.component';
import { LoginComponent } from './pages/login/login.component';
import { LayoutComponent } from './core/components/layout/layout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { RouterModule } from '@angular/router';
import { ProfileComponent } from './pages/profile/profile.component';
import { CountdownModule } from 'ngx-countdown';
import { SortByPipe } from './core/pipes/sort-by.pipe';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CategoryNavigationListComponent } from './components/categories/category-navigation-list/category-navigation-list.component';
import { CategoryListItemComponent } from './components/categories/category-list-item/category-list-item.component';
import { AuctionsComponent } from './pages/auctions/auctions.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { AuctionsSearchComponent } from './pages/auctions-search/auctions-search.component';
import { AuctionDetailComponent } from './pages/auction-detail/auction-detail.component';
import { AuctionListItemComponent } from './components/auctions/auction-list-item/auction-list-item.component';
import { AuctionListComponent } from './components/auctions/auction-list/auction-list.component';
import { BidComponent } from './components/auctions/bid/bid.component';
import { AuctionEditComponent } from './pages/auction-edit/auction-edit.component';
import { CategoryEditComponent } from './pages/category-edit/category-edit.component';
import { ProfileEditComponent } from './pages/profile-edit/profile-edit.component';
import { SearchComponent } from './components/auctions/search/search.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { AuctionsFilteredComponent } from './pages/auctions-filtered/auctions-filtered.component';


@NgModule({
  declarations: [
    AppComponent,
    AuctionListComponent,
    AuctionDetailComponent,
    BidComponent,
    HeaderComponent,
    FooterComponent,
    RegisterComponent,
    SideBarComponent,
    LoginComponent,
    LayoutComponent,
    AuctionListItemComponent,
    ProfileComponent,
    ProfileEditComponent,
    SortByPipe,
    CategoryNavigationListComponent,
    CategoriesComponent,
    CategoryListItemComponent,
    CategoryEditComponent,
    AuctionsComponent,
    AuctionEditComponent,
    CategoriesComponent,
    AuctionsSearchComponent,
    SearchComponent,
    ForgotPasswordComponent,
    ChangePasswordComponent,
    AuctionsFilteredComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    CoreModule,
    RouterModule,
    CountdownModule,
    FlexLayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
