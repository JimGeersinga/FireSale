import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { DisplayType } from 'src/app/shared/models/display-type.enum';
import { FilterDTO } from 'src/app/shared/models/filterDTO';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-auctions-search',
  templateUrl: './auctions-search.component.html',
  styleUrls: ['./auctions-search.component.scss']
})
export class AuctionsSearchComponent implements OnInit {
  public featuredAuctions: AuctionDTO[] = [];
  public filteredAuctions: AuctionDTO[] = [];
  public categories: CategoryDTO[] = [];
  public categoryName = 'Alle veilingen';
  public displayTypeEnum = DisplayType;
  public displayType: DisplayType = DisplayType.LIST;

  constructor(
    private activeRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private auctionService: AuctionService
  ) { }

  ngOnInit(): void {
    this.loadCategories().subscribe((categories) => {
      this.activeRoute.queryParams.subscribe((queryParams) => {
        if (queryParams?.c) {
          this.categoryName = categories.find((category) => category.id === +queryParams?.c)?.name;
        } else {
          this.categoryName = 'Gevonden veilingen';
        }
        this.loadFilteredAuctions(queryParams?.q, queryParams?.t, +queryParams?.c);
      });
    });
  }

  loadCategories(): Observable<CategoryDTO[]> {
    return new Observable(observer => {
      this.categoryService
        .get()
        .subscribe((response) => {
          this.categories = response.data;
          observer.next(response.data);
        });
    });
  }

  loadFilteredAuctions(search?: string, tag?: string, categoryId?: number): void {
    const filterModel: FilterDTO = { tags: [], categories: [], name: null };
    if (search) {
      filterModel.name = search;
    }
    if (tag) {
      filterModel.tags.push(tag);
    }
    if (categoryId) {
      filterModel.categories.push(categoryId);
    }

    this.filteredAuctions = null;
    this.auctionService
      .getFiltered(filterModel)
      .subscribe((response) => this.filteredAuctions = response.data);
  }
}
