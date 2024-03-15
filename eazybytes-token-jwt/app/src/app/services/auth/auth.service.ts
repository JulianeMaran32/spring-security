import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user = new User();

  constructor(private router: Router) { }

  canActivate(): boolean {

    if(sessionStorage.getItem('userdetails')){
        this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    }

    if(!this.user){
        this.router.navigate(['login']);
    }

    return this.user ? true : false;

  }

}
