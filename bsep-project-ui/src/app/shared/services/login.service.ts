import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/User';
import { LoginData } from '../models/LoginData';
import { environment } from 'src/environments/environment';
import { ApiPaths } from 'src/environments/ApiPaths';
import { LoginResponseData } from '../models/LoginResponseData';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  get token(): string | null {
    return localStorage.getItem('access-token');
  }

  get user(): User | null {
    let user: string | null = localStorage.getItem('user-data');
    if (user !== null) return JSON.parse(user) as User;
    else return null;
  }

  get isTokenPresent(): boolean {
    return this.token !== null;
  }

  sendLoginRequest(data: LoginData): Observable<LoginResponseData> {
    let url = `${environment.baseUrl}/${ApiPaths.Auth}/login`;
    return this.http.post<LoginResponseData>(url, data);
  }

  setUserData(data: LoginResponseData): void {
    localStorage.clear();
    localStorage.setItem('access-token', data.accessToken);
    localStorage.setItem('user-data', JSON.stringify(data.userDTO));
  }

  logout() {
    localStorage.clear();
  }
}
