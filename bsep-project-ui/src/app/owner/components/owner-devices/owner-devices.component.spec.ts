import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerDevicesComponent } from './owner-devices.component';

describe('OwnerDevicesComponent', () => {
  let component: OwnerDevicesComponent;
  let fixture: ComponentFixture<OwnerDevicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerDevicesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerDevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
