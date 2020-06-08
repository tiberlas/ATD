import { Component, OnInit, OnDestroy } from '@angular/core';
import { LoggerService } from '../service/logger.service';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit, OnDestroy {

  showDiv: string;

  constructor(private log: LoggerService) { }

  ngOnInit(): void {
    this.showDiv = 'LOGGER';
    this.log.WsConnect();
  }

  onChangeDiv(state: string) {
    if (state === 'LOGGER' || state === 'ACL' || state === 'START') {
      this.showDiv = state;
    } else {
      this.showDiv = 'LOGGER';
    }
  }

  ngOnDestroy(): void {
    this.log.WsDisconnect();
  }

}
