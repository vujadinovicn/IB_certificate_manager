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
}

export interface UserDTO {
  name: string;
  surname: string;
  telephoneNumber: string;
  email: string;
  password: string;
}
