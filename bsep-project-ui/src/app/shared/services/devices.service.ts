import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Realestate } from 'src/app/shared/models/Realestate';
import { ApiPaths } from 'src/environments/ApiPaths';
import { environment } from 'src/environments/environment';
import { NewDeviceDTO } from '../dto/NewDeviceDTO';
import { Device } from '../models/Device';

@Injectable({
  providedIn: 'root'
})
export class DevicesService {

  url: string = `${environment.baseUrl}/${ApiPaths.Devices}`;

  constructor(private http: HttpClient) {}

  public getAll():Observable<Device[]>{
    let url = this.url+"/all";
    return this.http.get<Device[]>(url);
  }

  public getForRealestate(realestateID:number):Observable<Device[]>{
    let url = this.url+"/forRealestate/"+realestateID;
    return this.http.get<Device[]>(url);
  }

  public turnOnOfNotification(deviceID:number):Observable<Device>{
    let url = this.url+"/turnOnOfNotifications/"+deviceID;
    return this.http.get<Device>(url);  
  }

  public addNewDevice(newDeviceDTO: NewDeviceDTO ){
    let url = this.url+"/addNew";
    return this.http.post<Device>(url,newDeviceDTO);  
  }


}
