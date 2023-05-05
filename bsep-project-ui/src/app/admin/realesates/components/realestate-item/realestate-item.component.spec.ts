import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealestateItemComponent } from './realestate-item.component';

describe('RealestateItemComponent', () => {
  let component: RealestateItemComponent;
  let fixture: ComponentFixture<RealestateItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealestateItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RealestateItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
