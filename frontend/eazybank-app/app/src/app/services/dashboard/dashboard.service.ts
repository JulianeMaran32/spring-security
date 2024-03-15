import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppConstants } from 'src/app/constants/app.constants';
import { Contact } from 'src/app/model/contact';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  // rooturl = 'http://localhost:8585';

  constructor(private http:HttpClient) { }

  getAccountDetails(id: number){
    return this.http.get(environment.rooturl + AppConstants.ACCOUNT_API_URL + "?id=" + id, {
      observe: 'response',
      withCredentials: true
    });
  }

  getAccountTransactions(id: number){
    return this.http.get(environment.rooturl + AppConstants.BALANCE_API_URL + "?id=" + id, {
      observe: 'response',
      withCredentials: true
    });
  }

  getLoansDetails(id: number){
    return this.http.get(environment.rooturl + AppConstants.LOANS_API_URL + "?id=" + id, {
      observe: 'response',
      withCredentials: true
    });
  }

  getCardsDetails(id: number){
    return this.http.get(environment.rooturl + AppConstants.CARDS_API_URL + "?id=" + id, {
      observe: 'response',
      withCredentials: true
    });
  }

  getNoticeDetails(){
    return this.http.get(environment.rooturl + AppConstants.NOTICES_API_URL, {
      observe: 'response'
    });
  }

  saveMessage(contact : Contact){
    var contacts = [];
    contacts.push(contact);
    return this.http.post(environment.rooturl + AppConstants.CONTACT_API_URL, contacts, {
      observe: 'response'
    });
  }

}
