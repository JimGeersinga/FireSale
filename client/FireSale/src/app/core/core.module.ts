import { APP_INITIALIZER, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomErrorStateMatcher } from './providers/CustomErrorStateMatcher';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthInterceptor } from './interceptors/basic-auth.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { ConfigService } from './services/config.service';
import { HomeComponent } from '../pages/home/home.component';
import { BlobToImagePipe } from './pipes/blob-to-image.pipe';

const appInitializerFn = (appConfig: ConfigService) => {
  return () => {
    return appConfig.load();
  };
};


@NgModule({
  declarations: [HomeComponent, BlobToImagePipe],
  imports: [
    CommonModule
  ],
  providers: [
    CustomErrorStateMatcher,
    ConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFn,
      multi: true,
      deps: [ConfigService]
    },
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  exports: [
    HomeComponent,
    BlobToImagePipe
  ]
})
export class CoreModule { }
