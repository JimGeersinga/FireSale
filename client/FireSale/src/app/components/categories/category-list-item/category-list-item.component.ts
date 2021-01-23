import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryUpsertDTO } from 'src/app/shared/models/categoryUpsertDto';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-category-list-item',
  templateUrl: './category-list-item.component.html',
  styleUrls: ['./category-list-item.component.scss']
})
export class CategoryListItemComponent implements OnInit {
  @Input() public model: CategoryUpsertDTO;
  constructor(
    private categoryService: CategoryService,
    private router: Router) { }

  ngOnInit(): void {
  }

  delete(): void {
    this.model.archived = !this.model.archived;
    this.categoryService.put(this.model).subscribe();
  }
}
