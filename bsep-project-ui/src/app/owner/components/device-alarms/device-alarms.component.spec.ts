import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceAlarmsComponent } from './device-alarms.component';

describe('DeviceAlarmsComponent', () => {
  let component: DeviceAlarmsComponent;
  let fixture: ComponentFixture<DeviceAlarmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceAlarmsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceAlarmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
