
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