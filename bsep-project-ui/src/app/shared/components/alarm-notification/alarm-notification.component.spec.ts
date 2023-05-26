import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmNotificationComponent } from './alarm-notification.component';

describe('AlarmNotificationComponent', () => {
  let component: AlarmNotificationComponent;
  let fixture: ComponentFixture<AlarmNotificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmNotificationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
