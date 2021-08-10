import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  errMsg$ = new BehaviorSubject<string>('');
  constructor() { }


  setErrMsg(errstr: string){
    this.errMsg$.next(errstr);
  }

  getErrMsg(): Observable<string>{
    return this.errMsg$.asObservable();
  }


}
