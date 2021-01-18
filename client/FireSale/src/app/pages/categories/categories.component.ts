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
  data: CategoryUpsertDTO[] = [];
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
      this.categoryService.getArchived().subscribe((response) => {
      for (const item of response.data) {
        this.categories.push({
          id: item.id,
          name: item.name,
          archived: true
        });
      }
      this.data = this.categories;
      console.log(this.data);
      console.log(this.categories);
    });
    });
    

  }
  delete(id: number): void {
    const model = this.categories.find(x => x.id === id);
    model.archived = !model.archived;
    this.categoryService.put(model).subscribe(_ => {
    });
  }
  edit(id: number, e: any)
  {
    const newVal = e.target.value;
    const model = this.categories.find(x => x.id === id); 
    model.name = newVal;


     this.categoryService.put(model).subscribe(_ => {
    });

  }
}
