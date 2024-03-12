import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './components/account/account.component';
import { BalanceComponent } from './components/balance/balance.component';
import { CardsComponent } from './components/cards/cards.component';
import { ContactComponent } from './components/contact/contact.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { LoansComponent } from './components/loans/loans.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { NoticesComponent } from './components/notices/notices.component';
import { authGuard } from './routeguards/auth.guard';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'notices', component: NoticesComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'myAccount', component: AccountComponent, canActivate: [authGuard]  },
  { path: 'myBalance', component: BalanceComponent, canActivate: [authGuard]  },
  { path: 'myLoans', component: LoansComponent, canActivate: [authGuard]  },
  { path: 'myCards', component: CardsComponent, canActivate: [authGuard]  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
exports: [RouterModule]
})
export class AppRoutingModule { }
