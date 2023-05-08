import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegistrationData } from '../models/RegistrationData';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ApiPaths } from 'src/environments/ApiPaths';
import { CredentialsDTO } from 'src/app/user/models/CredentialsDTO';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  constructor(private http: HttpClient) {}

  sendRegistrationRequest(data: RegistrationData): Observable<any> {
    let url = `${environment.baseUrl}/${ApiPaths.Register}/register`;
    return this.http.post<RegistrationData>(url, data);
  }

  sendChangeCredentialsRequest(data: CredentialsDTO): Observable<any> {
    let url = `${environment.baseUrl}/${ApiPaths.Register}/changeCredentials`;
    return this.http.post<CredentialsDTO>(url, data);
  }
}
