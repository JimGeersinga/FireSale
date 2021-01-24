import { Component, Input, OnInit } from '@angular/core';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';

@Component({
  selector: 'app-category-navigation-list',
  templateUrl: './category-navigation-list.component.html',
  styleUrls: ['./category-navigation-list.component.scss'],
})
export class CategoryNavigationListComponent implements OnInit {
  constructor() { }

  @Input() public categories: CategoryDTO[];


  ngOnInit(): void { }
}
