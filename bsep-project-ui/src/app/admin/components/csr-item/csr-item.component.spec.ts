import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrItemComponent } from './csr-item.component';

describe('CsrItemComponent', () => {
  let component: CsrItemComponent;
  let fixture: ComponentFixture<CsrItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsrItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CsrItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
