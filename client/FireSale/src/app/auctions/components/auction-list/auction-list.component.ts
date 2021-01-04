import { Component, OnInit } from '@angular/core';
import { AuctionDTO } from '../../models/auctionDTO';
import { AuctionService } from '../../shared/auction.service';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { DisplayType } from '../../shared/display-type.enum';

@Component({
  selector: 'app-auction-list',
  templateUrl: './auction-list.component.html',
  styleUrls: ['./auction-list.component.scss'],
})
export class AuctionListComponent implements OnInit {
  public allAuctions: AuctionDTO[];
  public filteredAuctions: AuctionDTO[] = [];
  public categoryName = 'Alle veilingen';
  public displayTypeEnum = DisplayType;
  public displayType: DisplayType = DisplayType.LIST;
  public loadingCategoryAuctions = true;
  public loadingFeaturedAuctions = true;

  constructor(
    private activeRoute: ActivatedRoute,
    private categoryService: CategoryService,
    private auctionService: AuctionService
  ) {}

  ngOnInit(): void {
    this.activeRoute.queryParams.subscribe((queryParams) => {
      if (queryParams?.categoryId) {
        this.categoryService
          .get()
          .subscribe(
            (response) =>
              (this.categoryName = response.data.find(
                (category) => category.id === +queryParams?.categoryId
              ).name)
          );
      } else {
        this.categoryName = 'Alle veilingen';
      }
      this.listAuctions(queryParams?.categoryId);
    });
  }

  listAuctions(categoryId?: number): void {
    this.loadingFeaturedAuctions = true;
    this.loadingCategoryAuctions = true;
    this.auctionService.get().subscribe((response) => {
      this.loadingCategoryAuctions = false;
      this.loadingFeaturedAuctions = false;
      this.allAuctions = response.data;
      if (categoryId) {
        this.filteredAuctions = response.data.filter((auction) =>
          auction.categories.some((category) => category.id === categoryId)
        );
      } else {
        this.filteredAuctions = response.data;
      }
    });
  }
}
