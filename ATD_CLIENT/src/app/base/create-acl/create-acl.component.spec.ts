import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAclComponent } from './create-acl.component';

describe('CreateAclComponent', () => {
  let component: CreateAclComponent;
  let fixture: ComponentFixture<CreateAclComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAclComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAclComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
