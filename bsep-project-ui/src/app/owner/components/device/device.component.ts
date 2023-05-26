import { Component, Input, OnInit } from '@angular/core';
import { Device } from 'src/app/shared/models/Device';
import {
  faTemperature2,
  faVideo,
  faDoorOpen,
  faBell,
  faList,
  faCog
} from '@fortawesome/free-solid-svg-icons';
import { DevicesService } from 'src/app/shared/services/devices.service';
import { MatDialog } from '@angular/material/dialog';
import { DeviceAlarmsComponent } from '../device-alarms/device-alarms.component';
import { MessageService, MessageType } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-device',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.css']
})
export class DeviceComponent implements OnInit {

  @Input() device : Device | null = null;
  faTemperature = faTemperature2;
  faVideo = faVideo;
  faDoorOpen = faDoorOpen;
  faBell= faBell;
  faList = faList;
  faCog = faCog;

  constructor(
    private devicesService: DevicesService,
    private matDialog: MatDialog,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
  }

  turnOnOfNotification(){
    this.devicesService.turnOnOfNotification(this.device!.deviceID).subscribe(
      data =>{
        this.device = data;
      }
    );
  }

  openAlarmSettings(){
    const dialogRef = this.matDialog.open(DeviceAlarmsComponent,{data: this.device});
    dialogRef.afterClosed().subscribe(result => {
      this.messageService.showMessage("sacuvane promene",MessageType.SUCCESS);
    });
  }
}
