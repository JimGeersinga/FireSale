import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionDTO } from '../../models/auctionDTO';
import { AuctionService } from '../../shared/auction.service';

@Component({
  selector: 'app-new-auction',
  templateUrl: './new-auction.component.html',
  styleUrls: ['./new-auction.component.scss']
})
export class NewAuctionComponent implements OnInit {
  newAuctionForm = this.formBuilder.group(
    {
      name: ['', Validators.required],
      description: ['', Validators.required],
      minimalBid: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    }
  );
  public minDate:Date = new Date();
  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private auctionService:AuctionService
  ) { }

  ngOnInit(): void {
  }

  submitAuction(data: AuctionDTO) : void {
    if(data.endDate < data.startDate || new Date(data.startDate) < new Date())
    {
      this.dialog.open(OkDialogComponent, {data: {title: 'Nieuwe veiling', message: 'Start datum moet in de toekomst liggen en de eind datum moet daar voorbij liggen.'}});
    }else if(data.minimalBid < 1)
    {
      this.dialog.open(OkDialogComponent, {data: {title: 'Nieuwe veiling', message: 'Wij nemen aan dat je wel een minimum bod wilt hebben dus vul hiervoor een bedrag in.'}});
    }else{
      if (!this.newAuctionForm.valid) {
        this.dialog.open(OkDialogComponent, {data: {title: 'Nieuwe veiling', message: 'Gegevens voor een nieuwe veiling zijn niet correct ingevuld'}});
      }
      else {
          this.dialog.open(OkDialogComponent, {data: {title: 'Nieuwe veiling', message: 'Nieuwe veiling is gestart'}});
          this.auctionService.post(data);
      }
    }
  }
}

