import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { DisplayType } from 'src/app/shared/enums/display-type.enum';
import { FilterDTO } from 'src/app/shared/models/filterDTO';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-auctions',
  templateUrl: './auctions.component.html',
  styleUrls: ['./auctions.component.scss'],
})
export class AuctionsComponent implements OnInit {
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
    this.loadFeaturedAuctions();
    this.loadCategories().subscribe((categories) => {
      this.activeRoute.queryParams.subscribe((queryParams) => {
        if (queryParams?.categoryId) {
          this.categoryName = categories.find((category) => category.id === +queryParams?.categoryId)?.name;
        } else {
          this.categoryName = 'Alle veilingen';
        }
        this.loadFilteredAuctions(+queryParams?.categoryId);
      });
    });
  }

  loadFeaturedAuctions(): void {
    this.featuredAuctions = null;
    this.auctionService
      .getFeatured()
      .subscribe(response => {
        this.featuredAuctions = response.data;
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

  loadFilteredAuctions(categoryId?: number): void {
    const filterModel: FilterDTO = { tags: [], categories: [], name: null };
    if (categoryId) {
      filterModel.categories.push(categoryId);
    }

    this.filteredAuctions = null;
    this.auctionService
      .getFiltered(filterModel)
      .subscribe((response) => this.filteredAuctions = response.data);
  }
}
