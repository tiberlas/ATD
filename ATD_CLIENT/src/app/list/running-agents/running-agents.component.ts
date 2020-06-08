import { Component, OnInit } from '@angular/core';
import { RunningAgentService } from 'src/app/service/running-agent.service';
import { RunningAgentModel } from '../../model/running-agent.model';

@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  constructor(public runningAgents: RunningAgentService) { }

  ngOnInit(): void {
    this.runningAgents.getAllRunningAgents();
  }

  onStopAgent(agent: RunningAgentModel) {
    this.runningAgents.stopAgent(agent);
  }

}
