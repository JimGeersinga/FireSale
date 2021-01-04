import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoryUpsertDTO } from '../../models/categoryUpsertDto';
import { CategoryService } from '../../shared/category.service';

@Component({
  selector: 'app-category-create',
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.scss']
})
export class CategoryCreateComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private router: Router) { }
  
  public newCategory = this.formBuilder.group({
    name: ['test', Validators.required]
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

