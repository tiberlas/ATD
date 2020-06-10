import { Injectable, OnInit } from '@angular/core';
import { RunningAgentModel } from '../model/running-agent.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { webSocket } from 'rxjs/webSocket';
import { RunningWsAgentModel } from '../model/running-ws-agent.model';

const BASE_HTTP = environment.http;
const BASE_WS = environment.ws;

@Injectable({ providedIn: 'root' })
export class RunningAgentService {

    public runningAgentArray: RunningAgentModel[] = [];
    private ws;

    constructor(private http: HttpClient) { }

    public getAllRunningAgents() {
        this.http.get<RunningAgentModel[]>(BASE_HTTP + '/agents/running')
            .subscribe(data => {
                this.runningAgentArray = data;
            });
    }

    public createAgent(name: string, type: string) {
        this.http.put(BASE_HTTP + '/agents/running/' + type + '/' + name, {}, { responseType: 'text' })
            .subscribe();
    }

    public stopAgent(agent: RunningAgentModel) {
        this.http.delete(BASE_HTTP +
            '/agents/running/' +
            agent.hostAlias +
            '/' +
            agent.agentTypeName +
            '@' +
            agent.agentTypeModule +
            '/' +
            agent.agentName,
            { responseType: 'text' })
            .subscribe(data => {
                let i = this.runningAgentArray.indexOf(agent);

                if (i >= 0) {
                    this.runningAgentArray.splice(i, 1);
                }
            }, error => {
                alert('COULD NOT STOP AGENT');
            });
    }

    public WsConnect() {
        this.ws = webSocket(BASE_WS + '/agents');
        console.log('WEB SOCKET RUNNING AGENTS STARTED');
        this.ws.subscribe(
            (msg: RunningWsAgentModel) => {
                // alert('recived');
                if (msg.status === true) {
                    let i = this.findIndexInArray(msg.content);
                    if (i == -1) {
                        this.runningAgentArray.push(msg.content);
                    }
                } else {
                    let i = this.findIndexInArray(msg.content);
                    if (i > -1) {
                        this.runningAgentArray.splice(i, 1);
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

    private findIndexInArray(agentCheck: RunningAgentModel): number {
        let i = this.runningAgentArray.findIndex((agent) => {
            return (agent.agentName == agentCheck.agentName &&
                agent.agentTypeModule == agentCheck.agentTypeModule &&
                agent.agentTypeName == agentCheck.agentTypeName &&
                agent.hostAlias == agentCheck.hostAlias);
        });

        return i;
    }

}