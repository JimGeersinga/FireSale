import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss'],
})
export class CategoryListComponent implements OnInit {
  constructor(
    private router: Router,
    private categoryService: CategoryService
  ) {}

  public categories: CategoryDTO[];
  public categoryId: number;

  ngOnInit(): void {
    this.categoryService
      .get()
      .subscribe((response) => (this.categories = response.data));
  }

  public filterItemsByCategory(categoryId?: number): void {
    this.categoryId = categoryId;
    if (categoryId) {
      let navigationExtras: NavigationExtras = {
        queryParams: {
          categoryId: categoryId,
        },
      };
      this.router.navigate(['auctions'], navigationExtras);
    } else {
      this.router.navigate(['auctions']);
    }
  }
}
