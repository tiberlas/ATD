import { Component, OnInit } from '@angular/core';
import { LoggerService } from 'src/app/service/logger.service';
import { AclService } from 'src/app/service/acl.service';

@Component({
  selector: 'app-logger',
  templateUrl: './logger.component.html',
  styleUrls: ['./logger.component.css']
})
export class LoggerComponent implements OnInit {

  constructor(public logger: LoggerService, private acl: AclService) { }

  ngOnInit(): void {
  }

  onClearLog() {
    this.logger.clearLog();
  }

  onCN() {
    this.acl.startCN();
  }

}
