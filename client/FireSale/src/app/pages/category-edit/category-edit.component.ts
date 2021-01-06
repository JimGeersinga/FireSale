import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoryUpsertDTO } from '../../shared/models/categoryUpsertDto';
import { CategoryService } from '../../shared/services/category.service';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.scss']
})
export class CategoryEditComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private router: Router) { }

  public newCategory = this.formBuilder.group({
    name: ['', Validators.required]
  });
  ngOnInit(): void {
  }
  public submitCategory(data: CategoryUpsertDTO): void {
    data.archived = false;
    this.categoryService.post(data).subscribe(result => {
      this.router.navigate(['/categories']);
    });
  }
}

