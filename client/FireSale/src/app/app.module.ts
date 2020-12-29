import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuctionListComponent } from './auctions/components/auction-list/auction-list.component';
import { AuctionDetailComponent } from './auctions/components/auction-detail/auction-detail.component';
import { BidComponent } from './auctions/components/bid/bid.component';
import { UserListComponent } from './users/components/user-list/user-list.component';
import { HeaderComponent } from './core/header/header.component';
import { FooterComponent } from './core/footer/footer.component';
import { RegisterComponent } from './users/components/register/register.component';
import { SharedModule } from './shared/shared.module';
import { SideBarComponent } from './core/components/side-bar/side-bar.component';
import { LoginComponent } from './users/components/login/login.component';
import { LayoutComponent } from './core/components/layout/layout.component';
import { AuctionListItemComponent } from './auctions/components/auction-list-item/auction-list-item.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { RouterModule } from '@angular/router';
import { NewAuctionComponent } from './auctions/components/new-auction/new-auction.component';
import { ProfileComponent } from './users/components/profile/profile.component';
import { ProfileUpdateComponent } from './users/components/profile-update/profile-update.component';
import { CountdownModule } from 'ngx-countdown';
import { SortByPipe } from './core/pipes/sort-by.pipe';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CategoryListComponent } from './auctions/components/category-list/category-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AuctionListComponent,
    AuctionDetailComponent,
    BidComponent,
    UserListComponent,
    HeaderComponent,
    FooterComponent,
    RegisterComponent,
    SideBarComponent,
    LoginComponent,
    LayoutComponent,
    AuctionListItemComponent,
    NewAuctionComponent,
    ProfileComponent,
    ProfileUpdateComponent,
    SortByPipe,
    CategoryListComponent,
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
    FlexLayoutModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
