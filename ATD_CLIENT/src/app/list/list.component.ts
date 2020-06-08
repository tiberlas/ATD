import { Component, OnInit, OnDestroy } from '@angular/core';
import { ThrowStmt } from '@angular/compiler';
import { AgentTypeService } from '../service/agent-type.service';
import { RunningAgentService } from '../service/running-agent.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit, OnDestroy {

  toggleType: boolean;

  constructor(private type: AgentTypeService,
    private agent: RunningAgentService) { }

  ngOnInit(): void {
    this.type.WsConnect();
    this.agent.WsConnect();
  }

  ngOnDestroy(): void {
    this.type.WsDisconnect();
    this.agent.WsDisconnect();
  }

  onToggleType(): void {
    this.toggleType = !this.toggleType;
  }

}
