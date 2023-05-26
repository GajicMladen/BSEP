import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Realestate } from 'src/app/shared/models/Realestate';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';
import { Log } from '../models/Log';
import { LogDTO } from '../models/LogDTO';
import { LogSearchDTO } from '../models/LogSearchDTO';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  url: string = `${environment.baseUrl}/${ApiPaths.Logs}`;

  constructor(private http: HttpClient) {}

  public getAll():Observable<LogDTO[]>{
    let url = this.url+"/all";
    return this.http.get<LogDTO[]>(url);
  }

  

  public getLogsFilter(logSearchDTO:LogSearchDTO):Observable<LogDTO[]>{
    let url = this.url+"/search";
    return this.http.post<LogDTO[]>(url,logSearchDTO);
  }


}
