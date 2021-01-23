import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuctionUtil } from 'src/app/shared/auctionUtil';
import { AuctionState } from 'src/app/shared/enums/auction-state.enum';

import { AuctionListItemComponent } from './auction-list-item.component';

describe('AuctionListItemComponent', () => {
  let component: AuctionListItemComponent;
  let fixture: ComponentFixture<AuctionListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AuctionListItemComponent]
    }).compileComponents();

    spyOn(AuctionUtil, 'getState').and.returnValue(AuctionState.CLOSED);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuctionListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
