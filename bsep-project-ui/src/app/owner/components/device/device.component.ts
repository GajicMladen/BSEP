import { Component, Input, OnInit } from '@angular/core';
import { Device } from 'src/app/shared/models/Device';
import {
  faTemperature2,
  faVideo,
  faDoorOpen,
  faBell,
  faList
} from '@fortawesome/free-solid-svg-icons';
import { DevicesService } from 'src/app/shared/services/devices.service';

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

  constructor(
    private devicesService: DevicesService,
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
}
