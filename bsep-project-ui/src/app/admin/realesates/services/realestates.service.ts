import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Realestate } from 'src/app/user/models/Realestate';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RealestatesService {

  url: string = `${environment.baseUrl}/${ApiPaths.Realestates}`;

  constructor(private http: HttpClient) {}

  public getAll():Observable<Realestate[]>{
    let url = this.url+"/all";
    return this.http.get<Realestate[]>(url);
  }

  
  public getForUser(userId:number):Observable<Realestate[]>{
    let url = `${this.url}/user/${userId}`;
    return this.http.get<Realestate[]>(url);
  }

  public updateRealestateForUser(userId:number, rsIDs: string[] ):Observable<boolean>{
    let url = `${this.url}/update/forUser/${userId}?`;
    rsIDs.forEach(rsId => url+=  "rsid="+rsId+"&")
    return this.http.get<boolean>(url);   
  }


}
