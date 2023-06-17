import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';
import { LoginLogDTO } from '../models/LoginLog';

@Injectable({
  providedIn: 'root'
})
export class LoginLogsService {

  url: string = `${environment.baseUrl}/${ApiPaths.Logs}`;

  constructor(private http: HttpClient) {}

  public getAll():Observable<LoginLogDTO[]>{
    let url = this.url+"/login";
    return this.http.get<LoginLogDTO[]>(url);
  }
}
