import { APP_INITIALIZER, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { CustomErrorStateMatcher } from './providers/CustomErrorStateMatcher';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthInterceptor } from './interceptors/basic-auth.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { ConfigService } from './services/config.service';

const appInitializerFn = (appConfig: ConfigService) => {
  return () => {
    return appConfig.load();
  };
};


@NgModule({
  declarations: [LayoutComponent],
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
  ]
})
export class CoreModule { }
