import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountComponent } from './components/account/account.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { BalanceComponent } from './components/balance/balance.component';
import { CardsComponent } from './components/cards/cards.component';
import { ContactComponent } from './components/contact/contact.component';
import { LoansComponent } from './components/loans/loans.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { NoticesComponent } from './components/notices/notices.component';
import { HeaderComponent } from './components/header/header.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { XhrInterceptor } from './interceptors/app.request.interceptor';
import { authGuard } from './routeguards/auth.guard';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    BalanceComponent,
    CardsComponent,
    ContactComponent,
    DashboardComponent,
    HeaderComponent,
    HomeComponent,
    LoansComponent,
    LoginComponent,
    LogoutComponent,
    NoticesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions(
      {
        cookieName: 'XSRF-TOKEN',
        headerName: 'X-XSRF-TOKEN'
      }
    )
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: XhrInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
