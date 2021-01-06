import { Component, OnInit } from '@angular/core';
import { CategoryUpsertDTO } from 'src/app/shared/models/categoryUpsertDto';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  categories: CategoryUpsertDTO[] = [];
  displayedColumns: string[] = ['id', 'name', 'active'];
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.get().subscribe((response) => {
      for (const item of response.data) {
        this.categories.push({
          id: item.id,
          name: item.name,
          archived: false
        });
      }
    });
    this.categoryService.getArchived().subscribe((response) => {
      for (const item of response.data) {
        this.categories.push({
          id: item.id,
          name: item.name,
          archived: true
        });
      }
    });

  }
}
