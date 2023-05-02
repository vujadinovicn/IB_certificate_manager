import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    skip: 'true',
  });

  private user$ = new BehaviorSubject<User|null>(null);

  constructor(private http: HttpClient) { }

  getUser(): Observable<any> {
    return this.user$;
  }

  setUser(): void {
    this.user$.next(this.readUserFromStorage());
  }

  login(auth: any): Observable<TokenDTO> {
    return this.http.post<TokenDTO>(environment.apiHost + '/user/login', auth, {
      headers: this.headers
    });
  }

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('refreshToken');
    this.setUser();
  }

  refresh(): Observable<TokenDTO> {
    const tokens = {
      accessToken: localStorage.getItem('user'),
      refreshToken: localStorage.getItem('refreshToken'),
    };
    return this.http.post<TokenDTO>(environment.apiHost + '/user/refresh', tokens, {
      headers: this.headers
    });
  }

  isLoggedIn(): boolean {
    if (localStorage.getItem('user') != null) {
      return true;
    }
    return false;
  }

  getToken() {
    return localStorage.getItem('user');
  }

  getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }

  getId(): any {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper = new JwtHelperService();
      const id = helper.decodeToken(accessToken).id;
      return id;
    }
    return -1;
  }

  getRole(): any {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper = new JwtHelperService();
      const role = helper.decodeToken(accessToken).role[0].authority;
      return role;
    }
    return null;
  }

  getEmail(): any {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper = new JwtHelperService();
      console.log(helper.decodeToken(accessToken));
      const email = helper.decodeToken(accessToken).sub;
      return email;
    }
    return null;
  }

  readUserFromStorage() : User|null {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(accessToken);
      const role = decodedToken.sub;
      const email = decodedToken.sub;
      const id = decodedToken.id;
      return {
        id: id,
        role: role,
        email: email
      }
    }
    return null;
  }

  isTokenExpired(): any {
    if (this.isLoggedIn()) {
      const accessToken: any = localStorage.getItem('user');
      const helper = new JwtHelperService();
      return helper.isTokenExpired(accessToken);
      
    }
    return false;
  }

}

export interface User {
  id: string,
  email: string,
  role: string
}

export interface TokenDTO {
  accessToken: Token;
  refreshToken: Token;
}