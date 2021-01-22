import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserService } from 'src/app/shared/services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  private errorMessages = {
    'LOGIN_FAILED': { title: 'Login mislukt', message: 'Je gebruikersnaam of wachtwoord is onjuist.' },
    'ACCOUNT_IS_LOCKED': { title: 'Account is geblokkeerd', message: 'Je account is geblokkeerd door een administrator.' },
    'VALIDATION_FAILED': { title: 'Validatie mislukt', message: 'Validatie mislukt.' },
    'UNAUTHORIZED': { title: 'Niet geautoriseerd', message: 'Je bent niet geautoriseerd om deze handeling uit te voeren of pagina te bekijken.' },
    'NOT_FOUND': { title: 'Niet gevonden', message: 'De data die je probeert te op te halen is niet gevonden.' },
    'AUCTION_NOT_FOUND': { title: 'Veiling niet gevonden', message: 'De veiling die je probeert op te halen is niet gevonden.' },
    'USER_NOT_FOUND': { title: 'Gebruiker niet gevonden', message: 'De gebruiker die je probeert op te halen is niet gevonden.' },
    'EXPIRED_RESET_TOKEN': { title: 'Reset token verlopen', message: 'Vraag opnieuw een wachtwoord reset aan.' },
    'INVALID_RESET_TOKEN': { title: 'Ongeldige reset token', message: 'Vraag opnieuw een wachtwoord reset aan.' },
    'TAG_NOT_FOUND': { title: 'Tag niet gevonden', message: 'De tag die je probeert op te halen is niet gevonden.' },
    'CATEGORY_NOT_FOUND': { title: 'Categorie niet gevonden', message: 'De categorie die je probeert op te halen is niet gevonden.' },
    'IMAGE_NOT_FOUND': { title: 'Afbeelding niet gevonden', message: 'De afbeelding die je probeert op te halen is niet gevonden.' },
    'BID_TOO_LOW': { title: 'Bieding mislukt', message: 'De bieding die je probeert te plaatsen is te laag.' },
    'AUCTION_ALREADY_COMPLETED': { title: 'Bieding mislukt', message: 'De veiling waarop je probeert te bieden is niet meer actief.' },
  }
  constructor(
    private userService: UserService,
    private dialog: MatDialog,
  ) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(catchError(err => {
      const error = err.error.errorMessage || err.statusText || 'Er is iets fout gegaan.';

      if (err.status === 401 && window.location.pathname !== '/login' && err.error.errorCode === 'LOGIN_FAILED') {
        // auto logout if 401 response returned from api
        this.userService.logout();
        location.reload();
      } else if (err.error.errorCode === 'USER_NOT_FOUND' && window.location.pathname !== '/forgotpassword') {
        // to prevent the 'user not found' dialog to pop up
        return null;
      } else {
        let data = {
          title: 'Fout',
          message: error
        };
        if (err.error.errorCode in this.errorMessages) {
          data = this.errorMessages[err.error.errorCode];
        }
        this.dialog.open(OkDialogComponent, {
          data
        });
      }

      return throwError(error);
    }));
  }
}
