import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Device } from 'src/app/shared/models/Device';
import { DevicesService } from 'src/app/shared/services/devices.service';

@Component({
  selector: 'app-device-alarms',
  templateUrl: './device-alarms.component.html',
  styleUrls: ['./device-alarms.component.css']
})
export class DeviceAlarmsComponent implements OnInit {

  form : FormGroup = new FormGroup({
    minTemp: new FormControl(),
    maxTemp: new FormControl()
  });

  constructor(
    public dialogRef: MatDialogRef<DeviceAlarmsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Device,
    private devicesService: DevicesService
    ) { }

  ngOnInit(): void {
    this.form.get("minTemp")?.setValue(this.data.down_limit);
    this.form.get("maxTemp")?.setValue(this.data.up_limit);
  }

  save(){
    console.log(this.form.get("minTemp")?.value);
    console.log(this.form.get("maxTemp")?.value);
    this.data.up_limit = this.form.get("maxTemp")?.value;
    this.data.down_limit = this.form.get("minTemp")?.value;
    this.devicesService.editAlarmValues(this.data).subscribe(
      data=>{
        this.dialogRef.close();
      }
    );
  }
}
