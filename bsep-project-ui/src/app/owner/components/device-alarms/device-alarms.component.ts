import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeviceType } from 'src/app/shared/enums/DeviceType';
import { Device } from 'src/app/shared/models/Device';
import { DevicesService } from 'src/app/shared/services/devices.service';
import { MessageService, MessageType } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-device-alarms',
  templateUrl: './device-alarms.component.html',
  styleUrls: ['./device-alarms.component.css']
})
export class DeviceAlarmsComponent implements OnInit {

  isForTemeprature : boolean = false;

  form : FormGroup = new FormGroup({
    minTemp: new FormControl(),
    maxTemp: new FormControl(),
    occurrencesNumber: new FormControl(),
    timeRangeMinutes: new FormControl(),
  });

  constructor(
    public dialogRef: MatDialogRef<DeviceAlarmsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Device,
    private devicesService: DevicesService,
    private messageService: MessageService
    ) {
      this.isForTemeprature = data.deviceType == DeviceType.TEMPERATURE;
     }

  ngOnInit(): void {
    this.form.get("minTemp")?.setValue(this.data.downLimit);
    this.form.get("maxTemp")?.setValue(this.data.upLimit);
    this.form.get("occurrencesNumber")?.setValue(this.data.occurrencesNumber);
    this.form.get("timeRangeMinutes")?.setValue(this.data.timeRangeMinutes);
  }

  save(){
    this.data.upLimit = this.form.get("maxTemp")?.value;
    this.data.downLimit = this.form.get("minTemp")?.value;
    this.data.occurrencesNumber = this.form.get("occurrencesNumber")?.value;
    this.data.timeRangeMinutes = this.form.get("timeRangeMinutes")?.value;
    this.devicesService.editAlarmValues(this.data).subscribe(
      data=>{
        this.dialogRef.close();
        this.messageService.showMessage("sacuvane promene",MessageType.SUCCESS);
      }
    );
  }
}
