import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BaseComponent } from './base/base.component';
import { ListComponent } from './list/list.component';
import { AgentTypesComponent } from './list/agent-types/agent-types.component';
import { RunningAgentsComponent } from './list/running-agents/running-agents.component';
import { AgentComponent } from './list/running-agents/agent/agent.component';
import { TypeComponent } from './list/agent-types/type/type.component';
import { LoggerComponent } from './base/logger/logger.component';
import { StartAgentComponent } from './base/start-agent/start-agent.component';
import { CreateAclComponent } from './base/create-acl/create-acl.component';


@NgModule({
  declarations: [
    AppComponent,
    BaseComponent,
    ListComponent,
    AgentTypesComponent,
    RunningAgentsComponent,
    AgentComponent,
    TypeComponent,
    LoggerComponent,
    StartAgentComponent,
    CreateAclComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AngularFontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
