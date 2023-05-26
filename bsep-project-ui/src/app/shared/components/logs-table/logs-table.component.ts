import { Component, OnInit } from '@angular/core';
import { LogDTO } from '../../models/LogDTO';
import { LogsService } from '../../services/logs.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Realestate } from '../../models/Realestate';
import { Device } from '../../models/Device';
import { LoginService } from '../../services/login.service';
import { RealestatesService } from '../../services/realestates.service';
import { DevicesService } from '../../services/devices.service';
import { LogSearchDTO } from '../../models/LogSearchDTO';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-logs-table',
  templateUrl: './logs-table.component.html',
  styleUrls: ['./logs-table.component.css'],
})
export class LogsTableComponent implements OnInit {
  logs: LogDTO[] = [];
  realestates: Realestate[] = [];
  devices: Device[] = [];
  
  form = new FormGroup({
    realestate: new FormControl(),
    device: new FormControl(),
    dateRange : new FormGroup({
      start: new FormControl(),
      end: new FormControl(),
    })
  });

  getDateRange():FormGroup{
    return this.form.get('dateRange') as FormGroup;
  }

  displayedColumns: string[] = [
    'house',
    'device',
    'exactTime',
    'receivedValue',
  ];
  constructor(
    private logsService: LogsService,
    private userService: LoginService,
    private realestateService: RealestatesService,
    private devicesService: DevicesService
  ) {}

  ngOnInit(): void {
    this.getAllLogs();
    
    this.realestateService
      .getForUser(this.userService.user!.id)
      .subscribe((data) => {
        this.realestates = data;
      });
  }

  getAllLogs(){

    this.logsService.getAll().subscribe((data) => {
      this.logs = data;
      console.log(this.logs);
    });
  }

  realestateChanged() {
    this.devicesService
      .getForRealestate(this.form.get('realestate')!.value)
      .subscribe((data) => {
        this.devices = data;
        this.form.get('device')?.setValue(null);
        this.filterLogs();
      });
  }

  filterLogs(){
    let logSearchDTO : LogSearchDTO = {
      realestateID: 0,
      deviceID: 0,
      startDate: null,
      endDate: null
    };
    logSearchDTO!.realestateID = this.form.get('realestate')?.value;
    logSearchDTO!.deviceID = this.form.get('device')?.value;
    let dateRange = this.getDateRange().value;
    try{
      logSearchDTO!.startDate = this.formatDate(dateRange['start'])+" 00:00";
      logSearchDTO!.endDate = this.formatDate(dateRange['end']) + " 23:59";
    }
    catch(e){
      logSearchDTO!.startDate =null;
      logSearchDTO!.endDate =null;
      
    }
    console.log(logSearchDTO);
    if(logSearchDTO.realestateID != null){
      this.logsService.getLogsFilter(logSearchDTO).subscribe(
        data => {
          this.logs= data;
        }
      );
    }else{
      this.getAllLogs();
    }

  }
  

  formatDate(obj:any):string{
    return `${obj.getFullYear()}-${('0'+ (obj.getMonth()+1)).slice(-2)}-${('0'+ obj.getDate()).slice(-2)}`
  }

}
