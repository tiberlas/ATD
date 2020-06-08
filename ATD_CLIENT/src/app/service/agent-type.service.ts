import { Injectable, OnInit } from '@angular/core';
import { AgentTypeModel } from '../model/agent-type.model';
import { base_url } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { webSocket } from 'rxjs/webSocket';
import { AgentWsTypeModel } from '../model/agent-ws-type.model';

const BASE_HTTP = base_url.http;
const BASE_WS = base_url.ws;

@Injectable({ providedIn: 'root' })
export class AgentTypeService {

    public agentTypeArray: AgentTypeModel[] = [];
    private ws;

    constructor(private http: HttpClient) { }

    public getAllTypes() {
        return this.http.get<AgentTypeModel[]>(BASE_HTTP + '/agents/classes')
            .subscribe(data => {
                this.agentTypeArray = data;
            });
    }

    public WsConnect() {
        this.ws = webSocket(BASE_WS + '/type');
        console.log('WEB SOCKET TYPES STARTED');
        this.ws.subscribe(
            (msg: AgentWsTypeModel) => {
                // alert('recived');
                if (msg.status === true) {
                    let i = this.findIndexInArray(msg.content);
                    if (i == -1) {
                        this.agentTypeArray.push(msg.content);
                    }
                } else {
                    let i = this.findIndexInArray(msg.content);
                    if (i > -1) {
                        this.agentTypeArray.splice(i, 1);
                    }
                }
            },
            (err) => console.log(err),
            () => console.log('complete')
        );

    }

    public WsDisconnect() {
        this.ws.unsubscribe();
    }

    private findIndexInArray(typeCheck: AgentTypeModel): number {
        let i = this.agentTypeArray.findIndex((type) => {
            return (type.module === typeCheck.module) &&
                (type.type === typeCheck.type)
        });

        return i;
    }

}