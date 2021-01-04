import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryDTO } from '../../models/categoryDto';
import { CategoryUpsertDTO } from '../../models/categoryUpsertDto';
import { CategoryService } from '../../shared/category.service';

@Component({
  selector: 'app-category-list-item',
  templateUrl: './category-list-item.component.html',
  styleUrls: ['./category-list-item.component.scss']
})
export class CategoryListItemComponent implements OnInit {
  @Input() public model: CategoryUpsertDTO;
  constructor(private categoryService:CategoryService,private router: Router) { }

  ngOnInit(): void {
  }
  delete() {
    this.model.archived = !this.model.archived;
    this.categoryService.put(this.model).subscribe(_ => { 
    });
  }
}
