import { Injectable } from '@angular/core';
import { User } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private certificatesToDisplay = new BehaviorSubject<Cerificate[]>([]);

  constructor(private http: HttpClient) { }

  setCertificatesToDisplay(certificates: Cerificate[]) {
    this.certificatesToDisplay.next(certificates);
  }

  getCertificatesToDisplay() {
    return this.certificatesToDisplay.asObservable();
  }

  getMyCertificates(): Observable<any> {
    return this.http.get<any>(environment.apiHost + "/certificate/mine");
  }

  getAllCertificates(): Observable<any> {
    return this.http.get<any>(environment.apiHost + "/certificate");
  }

  validateByUpload(file: String): Observable<any> {
    const options: any = {
        responseType: 'json',
      };
    return this.http.post<any>(environment.apiHost + "/certificate/validate-upload", file, options);
  }

  validateBySerialNumber(serialNumber: String): Observable<any> {
    const options: any = {
        responseType: 'json',
      };
    return this.http.get<any>(environment.apiHost + "/certificate/validate/" + serialNumber, options);
  }

  withdraw(serialNumber: String, reason: String): Observable<any> {
    const options: any = {
        responseType: 'json',
      };
    return this.http.put<any>(environment.apiHost + "/certificate/withdraw/" + serialNumber, {reason: reason}, options);
  }


}

export interface Cerificate {
  id: number,
  serialNumber: string,
  validFrom: string,
  validTo: string,
  valid: boolean,
  type: any,
  issuer: User,
  issuedTo: User
}
