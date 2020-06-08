import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { RunningAgentModel } from 'src/app/model/running-agent.model';

@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.css']
})
export class AgentComponent implements OnInit {

  @Input('agent') agent: RunningAgentModel;
  @Output('stop') stop: EventEmitter<RunningAgentModel> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onStop() {
    this.stop.emit(this.agent);
  }

}
