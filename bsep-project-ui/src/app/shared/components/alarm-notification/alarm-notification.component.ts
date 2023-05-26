import { Component, OnInit } from '@angular/core';
import { WebSocketAPI } from '../../websocket/web-socket-api';
import { MessageService, MessageType } from '../../services/message.service';

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
  ) { }

  ngOnInit() {
    
    this.webSocketAPI = new WebSocketAPI(this);
    console.log("povezan");
    this.connect();
  }

  connect(){
    this.webSocketAPI!._connect();
  }

  disconnect(){
    this.webSocketAPI!._disconnect();
  }

  sendMessage(){
    this.webSocketAPI!._send(this.name);
  }

  handleMessage(message:string){
    this.messageService.showMessage(message,MessageType.WARNING);
  }
}
