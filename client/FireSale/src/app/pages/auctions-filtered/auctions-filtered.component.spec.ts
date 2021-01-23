import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuctionsFilteredComponent } from './auctions-filtered.component';

describe('AuctionsFilteredComponent', () => {
  let component: AuctionsFilteredComponent;
  let fixture: ComponentFixture<AuctionsFilteredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuctionsFilteredComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuctionsFilteredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
