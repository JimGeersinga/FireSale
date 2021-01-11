import { Component, HostListener, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-category-navigation-list',
  templateUrl: './category-navigation-list.component.html',
  styleUrls: ['./category-navigation-list.component.scss'],
})
export class CategoryNavigationListComponent implements OnInit {
  constructor(
    private router: Router,
    private categoryService: CategoryService
  ) { }

  public categories: CategoryDTO[];
  public categoryId: number;

  private toolbarHeight = document.querySelector('.mat-toolbar').clientHeight;
  private sectionPadding = 25;

  ngOnInit(): void {
    this.categoryService
      .get()
      .subscribe((response) => (this.categories = response.data));
  }

  public filterItemsByCategory(categoryId?: number): void {
    this.categoryId = categoryId;
    if (categoryId) {
      const navigationExtras: NavigationExtras = {
        queryParams: {
          categoryId,
        },
      };
      this.router.navigate(['auctions'], navigationExtras);
    } else {
      this.router.navigate(['auctions']);
    }
  }

  @HostListener('window:scroll', ['$event'])
  public onWindowScroll(event: Event): void {
    const container: HTMLDivElement = document.querySelector('.left-side');
    const distanceToTop = container.getBoundingClientRect().top;
    const currentElement: HTMLDivElement = document.querySelector('.category-list');
    const isFixed = (distanceToTop - this.sectionPadding <= this.toolbarHeight);
    currentElement.classList.toggle('sticky', isFixed);
  }
}
