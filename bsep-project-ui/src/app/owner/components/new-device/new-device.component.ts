import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NewDeviceDTO } from 'src/app/shared/dto/NewDeviceDTO';
import { DeviceType } from 'src/app/shared/enums/DeviceType';
import { DevicesService } from 'src/app/shared/services/devices.service';

@Component({
  selector: 'app-new-device',
  templateUrl: './new-device.component.html',
  styleUrls: ['./new-device.component.css']
})
export class NewDeviceComponent implements OnInit {

  form: FormGroup = new FormGroup({
    deviceType: new FormControl(),
    deviceDescription : new FormControl()
  });

  constructor(
    private devicesService:DevicesService,
    public dialogRef: MatDialogRef<NewDeviceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) { }

  ngOnInit(): void {
  }

  addNewDevice(){
    let newDeviceDTO : NewDeviceDTO = {
      deviceType: this.form.get('deviceType')?.value,
      deviceDescription: this.form.get('deviceDescription')?.value,
      realestateID: this.data
    };
    console.log(newDeviceDTO);
    this.devicesService.addNewDevice(newDeviceDTO).subscribe(
      data=>{
        this.dialogRef.close();
      }
    );
  }

}
