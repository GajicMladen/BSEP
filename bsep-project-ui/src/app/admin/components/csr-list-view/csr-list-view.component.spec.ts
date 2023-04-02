import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrListViewComponent } from './csr-list-view.component';

describe('CsrListViewComponent', () => {
  let component: CsrListViewComponent;
  let fixture: ComponentFixture<CsrListViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsrListViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CsrListViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
