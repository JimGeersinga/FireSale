import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuctionsSearchComponent } from './auctions-search.component';

describe('AuctionsSearchComponent', () => {
  let component: AuctionsSearchComponent;
  let fixture: ComponentFixture<AuctionsSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuctionsSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuctionsSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});