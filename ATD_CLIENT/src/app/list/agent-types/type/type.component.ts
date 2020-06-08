import { Component, OnInit, Input } from '@angular/core';
import { AgentTypeModel } from 'src/app/model/agent-type.model';

@Component({
  selector: 'app-type',
  templateUrl: './type.component.html',
  styleUrls: ['./type.component.css']
})
export class TypeComponent implements OnInit {

  @Input('type') type: AgentTypeModel;

  constructor() { }

  ngOnInit(): void {
  }

}
