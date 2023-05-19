import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  findById(id: number): Observable<any> {
    return this.http.get<any>(environment.apiHost + "/user/" + id);
  }
}
export interface UserDTO {
  name: string;
  lastname: string;
  phoneNumber: string;
  email: string;
  password: string;
}