import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface SMSActivation {
  username: string,
  code: string
}

@Injectable({
  providedIn: 'root'
})
export class SmsCodeService {

  constructor(private http: HttpClient) { }

  activateBySms(smsActivation: SMSActivation): Observable<any> {
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    console.log(smsActivation);
    return this.http.post<any>(environment.apiHost, smsActivation, options);
  }
}
