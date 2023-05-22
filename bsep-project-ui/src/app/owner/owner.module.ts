import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OwnerDevicesComponent } from './components/owner-devices/owner-devices.component';
import { DeviceComponent } from './components/device/device.component';
import { OwnerRoutingModule } from './owner-routing.module';
import { MaterialModule } from '../material/material.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NewDeviceComponent } from './components/new-device/new-device.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    OwnerDevicesComponent,
    DeviceComponent,
    NewDeviceComponent
  ],
  imports: [
    CommonModule,
    OwnerRoutingModule,
    MaterialModule,
    FontAwesomeModule,
    ReactiveFormsModule
  ]
})
export class OwnerModule { }
