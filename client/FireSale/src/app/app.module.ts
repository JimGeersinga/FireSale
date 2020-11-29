import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuctionListComponent } from './auctions/auction-list/auction-list.component';
import { AuctionDetailComponent } from './auctions/auction-detail/auction-detail.component';
import { BidComponent } from './bids/bid/bid.component';
import { BidHistoryComponent } from './bids/bid-history/bid-history.component';
import { UserListComponent } from './users/components/user-list/user-list.component';
import { UserDetailComponent } from './users/components/user-detail/user-detail.component';
import { HeaderComponent } from './core/header/header.component';
import { FooterComponent } from './core/footer/footer.component';
import { RegisterComponent } from './users/components/register/register.component';
import { SharedModule } from './shared/shared.module';
import { SideBarComponent } from './core/side-bar/side-bar.component';
import { LoginComponent } from './users/components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ConfigService } from './core/services/config.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthInterceptor} from './core/interceptors/basic-auth.interceptor';
import { ErrorInterceptor } from './core/interceptors/error.interceptor';

const appInitializerFn = (appConfig: ConfigService) => {
  return () => {
    return appConfig.load();
  };
};

@NgModule({
  declarations: [
    AppComponent,
    AuctionListComponent,
    AuctionDetailComponent,
    BidComponent,
    BidHistoryComponent,
    UserListComponent,
    UserDetailComponent,
    HeaderComponent,
    FooterComponent,
    RegisterComponent,
    SideBarComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    ConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFn,
      multi: true,
      deps: [ConfigService]
    },
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
