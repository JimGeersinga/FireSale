import { Component, HostListener, Input, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-category-navigation-list',
  templateUrl: './category-navigation-list.component.html',
  styleUrls: ['./category-navigation-list.component.scss'],
})
export class CategoryNavigationListComponent implements OnInit {
  constructor(private router: Router) { }

  @Input() public categories: CategoryDTO[];

  private toolbarHeight = document.querySelector('.mat-toolbar').clientHeight;
  private sectionPadding = 25;

  ngOnInit(): void {}

  public filterItemsByCategory(categoryId?: number): void {
    if (categoryId) {
      this.router.navigate(['auctions'],  {
        queryParams: {
          categoryId,
        },
      });
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
