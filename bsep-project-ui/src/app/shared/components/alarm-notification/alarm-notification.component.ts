import { Component, OnInit } from '@angular/core';
import { WebSocketAPI } from '../../websocket/web-socket-api';
import { MessageService, MessageType } from '../../services/message.service';
import { LoginService } from '../../services/login.service';
import { LogDTO } from '../../models/LogDTO';

@Component({
  selector: 'app-alarm-notification',
  templateUrl: './alarm-notification.component.html',
  styleUrls: ['./alarm-notification.component.css']
})
export class AlarmNotificationComponent implements OnInit {
  
  webSocketAPI: WebSocketAPI | undefined;
  greeting: any;
  name: string = "Mladens";

  constructor(
    private messageService:MessageService,
    private userService: LoginService
  ) {
    this.webSocketAPI = new WebSocketAPI(this,"alarms/"+this.userService.user?.id);
    this.webSocketAPI._connect();
   }

  ngOnInit() {
    console.log("napravljen alarm notif component");
    
  }


  handleMessage(message:string){
    console.log("dsadsada");
    let newLog: LogDTO = JSON.parse(JSON.parse(message).body);
    let messageToShow = `${newLog.house} - ${newLog.exactTime}\n ${newLog.device} \n ${newLog.receivedValue}`
    this.messageService.showMessage(messageToShow,MessageType.ERROR);
    console.log(message);
  }
}
