import { Component, OnInit } from '@angular/core';
import { AgentTypeService } from 'src/app/service/agent-type.service';

@Component({
  selector: 'app-agent-types',
  templateUrl: './agent-types.component.html',
  styleUrls: ['./agent-types.component.css']
})
export class AgentTypesComponent implements OnInit {

  constructor(public agentTypeService: AgentTypeService) { }

  ngOnInit(): void {
    this.agentTypeService.getAllTypes();

    //this.mock();  
  }

  private mock() {
    this.agentTypeService.agentTypeArray.push({ type: 'prikupljac', module: 'domen' });
    this.agentTypeService.agentTypeArray.push({ type: 'search', module: 'domen' });
  }

}
