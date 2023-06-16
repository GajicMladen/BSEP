import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { ComponentType } from '@angular/cdk/portal';
import { environment } from 'src/environments/environment';

export class WebSocketAPI {
  webSocketEndPoint: string = `${environment.baseUrl}/ws`;
  topic: string = '/topic/';
  stompClient: any;
  appComponent: any;

  constructor(appComponent: any, topic: string) {
    this.appComponent = appComponent;
    this.topic += topic;
  }

  _connect() {
    console.log('Initialize WebSocket Connection');
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect(
      {},
      function () {
        _this.stompClient.subscribe(_this.topic, function (sdkEvent: any) {
          _this.onMessageReceived(sdkEvent);
        });
        //_this.stompClient.reconnect_delay = 2000;
      },
      this.errorCallBack
    );
  }

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected');
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error: string) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  _send(message: any) {
    console.log('calling logout api via web socket');
    this.stompClient.send('/app/hello', {}, JSON.stringify(message));
  }

  onMessageReceived(message: string) {
    console.log('Message Recieved from Server :: ' + message);
    this.appComponent.handleMessage(JSON.stringify(message));
  }
}
