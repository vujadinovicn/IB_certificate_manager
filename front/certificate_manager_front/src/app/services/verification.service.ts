import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserDTO } from './user.service';

export interface ResetPasswordDTO{
  newPassword: string,
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

  sendVerificationMail(email: string): Observable<any>{
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.post<any>(environment.apiHost + "/user/send/verification/email/" + email, options);
  }

  sendVerificationSMS(email: string): Observable<any>{
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.post<any>(environment.apiHost + "/user/send/verification/sms/" + email, options);
  }

  verifyRegistration(verificationCode: number): Observable<any> {
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.get<any>(environment.apiHost + "/user/activate/"+verificationCode, options);
  }

  sendResetPasswordEmail(email: string): Observable<any>{
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.get<any>(environment.apiHost + "/user/reset/password/email/" + email, options);
  }

  sendResetPasswordSms(email: string): Observable<any>{
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    
    console.log(environment.apiHost + "/user/"+email+"/reset/password/sms");
    return this.http.get<any>(environment.apiHost + "/user/reset/password/sms/" + email, options);
  }

  resetPassword(resetPasswordDTO: ResetPasswordDTO): Observable<any>{
    const options: any = {
      responseType: 'json',
      rejectUnauthorized: false,
    };
    return this.http.put<any>(environment.apiHost + "/user/resetPassword", resetPasswordDTO, options);
  }

}
