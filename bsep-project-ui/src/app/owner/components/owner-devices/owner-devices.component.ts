import { Component, OnInit } from '@angular/core';
import { RealestatesService } from 'src/app/shared/services/realestates.service';
import { LoginService } from 'src/app/shared/services/login.service';
import { Realestate } from 'src/app/shared/models/Realestate';
import { Device } from 'src/app/shared/models/Device';
import { DevicesService } from 'src/app/shared/services/devices.service';
import {
  faHome,
  faListAlt,
  faPlusCircle
} from '@fortawesome/free-solid-svg-icons';
import { MatDialog } from '@angular/material/dialog';
import { NewDeviceComponent } from '../new-device/new-device.component';

@Component({
  selector: 'app-owner-devices',
  templateUrl: './owner-devices.component.html',
  styleUrls: ['./owner-devices.component.css']
})
export class OwnerDevicesComponent implements OnInit {

  realestates: Realestate[] = [];
  devices : {[id : number] : Device[]  } = {}
  faHome = faHome;
  faListAlt = faListAlt;
  faPlusCircle = faPlusCircle;

  constructor(
    private userService: LoginService,
    private realestateService: RealestatesService,
    private devicesService:DevicesService,
    private matDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.refreshData();
  }

  refreshData(){
    this.realestates=[];
    this.devices = {};
    this.realestateService.getForUser( this.userService.user!.id ).subscribe(
      data => {
        this.realestates = data;
        this.getDevicesForRealEstates();
      }
    )
  }

  getDevicesForRealEstates(){
    this.realestates.forEach( 
      realestate => {
        this.devicesService.getForRealestate(realestate.id).subscribe(
          data => {
            this.devices[realestate.id] = data;
          }
        )
      }
    )
  }

  addNewDevice(realestateIDs :number ){
    const dialogRef = this.matDialog.open(NewDeviceComponent,{data : realestateIDs});
    dialogRef.afterClosed().subscribe(result => {
      this.refreshData();
    });
  }

}
