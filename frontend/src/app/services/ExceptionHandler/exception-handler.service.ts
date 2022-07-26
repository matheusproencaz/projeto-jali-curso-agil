import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExceptionHandlerService {

  constructor() {}
    
  public handleError(error: any){
    // Fazer o tratamento dos erros de forma correta!
    
    if(error?.status === 403){
      return throwError(() => {})
    }

    if(error?.status === 422) {
      for(let err of error.error.errors){
        alert(err.message);
      }
      return throwError(() => {});
    }

    if(error?.status === 401){
      alert(error.error.message)
      return throwError(() => {})
    }

    let errMsg: string = error?.error?.message;
    // alert(error.error.message);
    return throwError(() => new Error(errMsg));
   }
}
