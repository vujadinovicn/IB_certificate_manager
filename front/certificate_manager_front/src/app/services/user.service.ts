import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  registerUser(user: UserDTO): Observable<any> {
    const options: any = {
      responseType: 'json',
    };
    return this.http.post<any>(environment.apiHost + "/user", user, options);
  }

  verify(code: String): Observable<any> {
    return this.http.get<any>(environment.apiHost + '/user/activate/' + code);
  }
}  

export interface UserDTO {
  name: string;
  lastname: string;
  phoneNumber: string;
  email: string;
  password: string;
}

export interface ResponseMessageDTO {
  message: string
}
