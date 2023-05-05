import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/user/models/User';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = `${environment.baseUrl}/${ApiPaths.Usres}`;

  constructor(private http: HttpClient) {}

  public getAll():Observable<User[]>{
    let url = this.url+"/all";
    return this.http.get<User[]>(url);
  }

  deleteUser(userId:number):Observable<boolean>{
    let url = this.url+"/delete/"+userId;
    return this.http.get<boolean>(url);   
  }


  changeUserRole(userId:number):Observable<boolean>{
    let url = this.url+"/changeUserRole/"+userId;
    return this.http.get<boolean>(url);   
  }
}
