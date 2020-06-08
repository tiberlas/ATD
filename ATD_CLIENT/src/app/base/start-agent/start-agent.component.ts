import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AgentTypeService } from 'src/app/service/agent-type.service';
import { RunningAgentService } from 'src/app/service/running-agent.service';

@Component({
  selector: 'app-start-agent',
  templateUrl: './start-agent.component.html',
  styleUrls: ['./start-agent.component.css']
})
export class StartAgentComponent implements OnInit {

  selectedType: string;

  constructor(public types: AgentTypeService, private agent: RunningAgentService) { }

  ngOnInit(): void {
    this.types.getAllTypes();
  }

  onCreateAgent(form: NgForm) {
    let name = form.value.InputName;

    if (name != null && name.trim() !== "" && this.selectedType !== "") {
      this.agent.createAgent(name, this.selectedType);
    }
  }

}
