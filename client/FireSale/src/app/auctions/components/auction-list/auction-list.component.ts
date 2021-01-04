import { Component, OnInit } from '@angular/core';
import { AuctionDTO } from '../../models/auctionDTO';
import { AuctionService } from '../../shared/auction.service';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-auction-list',
  templateUrl: './auction-list.component.html',
  styleUrls: ['./auction-list.component.scss'],
})
export class AuctionListComponent implements OnInit {
  public allAuctions: AuctionDTO[];
  public filteredAuctions: AuctionDTO[] = [];
  public categoryName: string = 'Alle veilingen';

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
    this.auctionService.get().subscribe((response) => {
      this.allAuctions = response.data;
      if (categoryId) {
        this.filteredAuctions = response.data.filter((auction) =>
          auction.categories.some((category) => category.id == categoryId)
        );
      } else {
        this.filteredAuctions = response.data;
      }
    });
  }
}
