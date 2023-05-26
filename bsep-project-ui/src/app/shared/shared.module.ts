import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RegistrationComponent } from './components/registration/registration.component';
import { LogsTableComponent } from './components/logs-table/logs-table.component';
import { AlarmNotificationComponent } from './components/alarm-notification/alarm-notification.component';

@NgModule({
  declarations: [NavbarComponent, NavbarComponent, LoginComponent, RegistrationComponent, LogsTableComponent, AlarmNotificationComponent],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    FontAwesomeModule,
    ReactiveFormsModule,
  ],
  exports: [NavbarComponent],
})
export class SharedModule {}
