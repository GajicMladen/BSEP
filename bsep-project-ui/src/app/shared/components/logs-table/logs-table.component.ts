import { Component, OnInit , OnDestroy, ViewChild, ElementRef } from '@angular/core';
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
import { WebSocketAPI } from '../../websocket/web-socket-api';
import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'
import { reduce } from 'rxjs';

@Component({
  selector: 'app-logs-table',
  templateUrl: './logs-table.component.html',
  styleUrls: ['./logs-table.component.css'],
})
export class LogsTableComponent implements OnInit,OnDestroy {
  logs: LogDTO[] = [];
  realestates: Realestate[] = [];
  devices: Device[] = [];
  
  webSocketAPI: WebSocketAPI | undefined;
  onlyAlarms = false;
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
  ) {
    this.webSocketAPI = new WebSocketAPI(this,"logs/"+this.userService.user?.id);
    this.webSocketAPI._connect();
  }

  ngOnInit(): void {
    this.getAllLogs();
    
    this.realestateService
      .getForUser(this.userService.user!.id)
      .subscribe((data) => {
        this.realestates = data;
      });
  }

  ngOnDestroy(){
    this.webSocketAPI?._disconnect();
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
      endDate: null,
      onlyAlarms: false
    };
    logSearchDTO!.realestateID = this.form.get('realestate')?.value;
    logSearchDTO!.deviceID = this.form.get('device')?.value;
    logSearchDTO.onlyAlarms = this.onlyAlarms;
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
  
  filterLogsAlarm(){
      this.onlyAlarms = !this.onlyAlarms;
      this.filterLogs();
  }

  formatDate(obj:any):string{
    return `${obj.getFullYear()}-${('0'+ (obj.getMonth()+1)).slice(-2)}-${('0'+ obj.getDate()).slice(-2)}`
  }

  handleMessage(){
    this.filterLogs();
  }

  @ViewChild('pdfTable', {static: false}) pdfTable!: ElementRef;


  public downloadAsPDF() {
    const doc = new jsPDF('l', 'mm', 'a4'); 
    
    const head = [['HOuse', 'Device', 'exact time', 'received value']]
    const data:any[] = [];
    this.logs.forEach(x => {
      data.push( [x.house, x.device, x.exactTime  ,x.receivedValue]);
    })

    autoTable(doc, {
        head: head,
        body: data,
        didDrawCell: (data) => { },
    });

    doc.save('table.pdf');
  }
}
