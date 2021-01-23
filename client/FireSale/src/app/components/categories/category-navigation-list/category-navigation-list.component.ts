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


  ngOnInit(): void { }
}
