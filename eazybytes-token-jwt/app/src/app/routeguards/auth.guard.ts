import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './../services/auth/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  return inject(AuthService).canActivate();
};
