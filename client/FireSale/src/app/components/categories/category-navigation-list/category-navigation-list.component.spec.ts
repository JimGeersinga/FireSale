import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryNavigationListComponent } from './category-navigation-list.component';

describe('CategoryNavigationListComponent', () => {
  let component: CategoryNavigationListComponent;
  let fixture: ComponentFixture<CategoryNavigationListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoryNavigationListComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryNavigationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
