import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { TagDTO } from 'src/app/shared/models/tagDto';
import { TagService } from 'src/app/shared/services/tag.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit, OnChanges {

  @Input() public categories: CategoryDTO[];

  public searchValue: string;
  public selectedCategory: CategoryDTO;
  public selectedTag: TagDTO;
  public tags: Observable<ApiResponse<TagDTO[]>>;

  constructor(
    private route: ActivatedRoute,
    private tagService: TagService,
    private router: Router
  ) { }

  ngOnInit(): void {

  }

  ngOnChanges(): void {
    this.route.queryParams.subscribe(params => {
      if (params) {
        this.selectedCategory = this.categories.find(c => c.id === +params.c);
        this.searchValue = params.q;
        if (params.t) {
          this.tags = this.tagService.searchTagsByName(params.t).pipe(tap(x => { this.selectedTag = x.data[0]; }));
        }
      }
    });
  }

  public autocompleteTag(event): void {
    const searchTerm = event.target.value;
    if (!searchTerm) {
      this.selectedTag = null;
    }
    this.tags = this.tagService.searchTagsByName(searchTerm);

  }

  public selectTag(event: MatAutocompleteSelectedEvent): void {
    this.selectedTag = event.option.value;
  }

  public search(): void {
    this.router.navigate(['/auctions/search'], { queryParams: { q: this.searchValue, t: this.selectedTag?.name, c: this.selectedCategory?.id } });
  }

}
