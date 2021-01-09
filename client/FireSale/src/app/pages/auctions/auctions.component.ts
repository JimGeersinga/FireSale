import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { DisplayType } from 'src/app/shared/models/display-type.enum';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-auctions',
  templateUrl: './auctions.component.html',
  styleUrls: ['./auctions.component.scss'],
})
export class AuctionsComponent implements OnInit {
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
      this.listAuctions(+queryParams?.categoryId);
    });
  }

  listAuctions(categoryId?: number): void {
    this.filteredAuctions = null;
    this.loadingFeaturedAuctions = true;
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
