import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http:HttpClient) { }

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


}
