import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionService } from '../../shared/auction.service';
import { CategoryDTO } from '../../../shared/models/categoryDto';
import { CategoryService } from '../../../shared/services/category.service';
import { CreateAuctionDTO } from '../../models/createAuctionDto';

@Component({
  selector: 'app-new-auction',
  templateUrl: './new-auction.component.html',
  styleUrls: ['./new-auction.component.scss'],
})
export class NewAuctionComponent implements OnInit {

  public selectedFiles = [];

  public newAuctionForm = this.formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    minimalBid: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    categories: ['', Validators.required],
    images: this.formBuilder.array([]),
  });


  public minDate: Date = new Date();
  public categories: CategoryDTO[];

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private auctionService: AuctionService,
    private router: Router,
    private categoryService: CategoryService,
    ) {}

  ngOnInit(): void {
     this.categoryService.get().subscribe(response => this.categories = response.data);
  }

  public selectFiles(event) {
    if (event.target.files && event.target.files[0]) {
      var filesAmount = event.target.files.length;
      for (let i = 0; i < filesAmount; i++) {
        var reader = new FileReader();
        reader.onload = (e: any) => {
          this.selectedFiles.push(e.target.result); // voorbeeld weergevan op pagina
        };
        reader.readAsDataURL(event.target.files[i]);
      }
    }
  }

  public submitAuction(data: CreateAuctionDTO): void {
    console.log(data);
    if (data.endDate < data.startDate || new Date(data.startDate) < new Date()) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Start datum moet in de toekomst liggen en de eind datum moet daar voorbij liggen.' } });
    } else if (data.minimalBid < 1) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Wij nemen aan dat je wel een minimum bod wilt hebben dus vul hiervoor een bedrag in.' } });
    } else {
      if (!this.newAuctionForm.valid) {
        this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Gegevens voor een nieuwe veiling zijn niet correct ingevuld' } });
      }
      else {
        this.selectedFiles.forEach((element) => {
          const encodedImage = element.split('base64,')[1];
          const fileExtension = "." + element.split(';')[0].split('/')[1];
          data.images.push({
            imageB64: encodedImage,
            type: fileExtension,
            sort: 0, // Moet nog aanpasbaar worden in UI
          });
        });
        this.auctionService.post(data).subscribe(result => {
          this.router.navigate(['/auctions/details', { queryParams: { id: result.data.id } }]);
        });
      }
    }
  }
}
