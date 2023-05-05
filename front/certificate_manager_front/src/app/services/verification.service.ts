import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserDTO } from './user.service';

export interface SMSActivation {
  email: string,
  code: string
}

@Injectable({
  providedIn: 'root'
})
export class VerificationService {

  private subject = new BehaviorSubject<any>({});

  sendUserDTO(message: UserDTO): void {
    this.subject.next(message);
  }

  recieveUserDTO(): Observable<UserDTO> {
    return this.subject.asObservable();
  }

  constructor(private http: HttpClient) { }

  activateBySms(smsActivation: SMSActivation): Observable<any> {
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.put<any>(environment.apiHost + "/sms/activate", smsActivation, options);
  }

  sendNewSms(userDTO: UserDTO): Observable<any> {
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.put<any>(environment.apiHost+"/sms", userDTO, options);
  }

  sendSms(userDTO: UserDTO): Observable<any> {
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    console.log(environment.apiHost+"/sms");
    return this.http.post<any>(environment.apiHost+"/sms", userDTO, options);
  }
}
