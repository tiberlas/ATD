import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { webSocket } from 'rxjs/webSocket';
import { AclModel } from '../model/acl.model';

const BASE_WS = environment.ws;

@Injectable({ providedIn: 'root' })
export class LoggerService {

    public log: string = '';
    private ws;

    public WsConnect() {
        this.ws = webSocket(BASE_WS + '/acl');
        console.log('WEB SOCKET ACL STARTED');
        this.ws.subscribe(
            (msg: AclModel) => {
                //alert("NEW MSG");
                this.log += "\n\n" + JSON.stringify(msg);
            },
            (err) => console.log(err),
            () => console.log('complete')
        );

    }

    public WsDisconnect() {
        this.ws.unsubscribe();
    }

    public clearLog() {
        this.log = "";
    }


}