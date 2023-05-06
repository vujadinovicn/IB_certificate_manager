import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http:HttpClient) { }

  findById(file: String): Observable<any> {
    const options: any = {
        responseType: 'json',
      };
    return this.http.post<any>(environment.apiHost + "/certificate/validate-upload", file, options);
  }
}
