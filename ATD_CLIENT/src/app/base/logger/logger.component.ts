import { Component, OnInit } from '@angular/core';
import { LoggerService } from 'src/app/service/logger.service';

@Component({
  selector: 'app-logger',
  templateUrl: './logger.component.html',
  styleUrls: ['./logger.component.css']
})
export class LoggerComponent implements OnInit {

  constructor(public logger: LoggerService) { }

  ngOnInit(): void {
  }

  onClearLog() {
    this.logger.clearLog();
  }

}
