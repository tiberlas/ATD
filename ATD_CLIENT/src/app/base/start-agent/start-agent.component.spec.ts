import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StartAgentComponent } from './start-agent.component';

describe('StartAgentComponent', () => {
  let component: StartAgentComponent;
  let fixture: ComponentFixture<StartAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StartAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
