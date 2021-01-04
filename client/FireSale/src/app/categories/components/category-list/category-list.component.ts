import { Component, OnInit } from '@angular/core';
import { CategoryDTO } from '../../models/categoryDto';
import { CategoryUpsertDTO } from '../../models/categoryUpsertDto';
import { CategoryService } from '../../shared/category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit {
  categories: CategoryUpsertDTO[] = [];
  displayedColumns: string[] = ['id', 'name', 'active'];
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.get().subscribe((response) => {
      for (let i = 0; i < response.data.length; i++)
      {
        this.categories.push({
          id: response.data[i].id,
          name: response.data[i].name,
          archived: false
        });
        
      }
      console.log(this.categories);
    });
    this.categoryService.getArchived().subscribe((response) => {
      for (let i = 0; i < response.data.length; i++)
      {
        this.categories.push({
          id: response.data[i].id,
          name: response.data[i].name,
          archived: true
        });
      }
      console.log(this.categories);
    });
    
  }
}
