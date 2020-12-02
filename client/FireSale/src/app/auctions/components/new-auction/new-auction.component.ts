import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionDto } from '../../models/auctionDto';

@Component({
  selector: 'app-new-auction',
  templateUrl: './new-auction.component.html',
  styleUrls: ['./new-auction.component.scss']
})
export class NewAuctionComponent implements OnInit {
  newAuctionForm = this.formBuilder.group(
    {
      auctionName: ['', Validators.required],
      auctionDescription: ['', Validators.required],
      minimalBid: [''],
      auctionStartdate: ['', Validators.required],
      auctionEnddate: ['', Validators.required]
    }
  );

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
  }

  submitAuction(data: AuctionDto) : void {
    if (!this.newAuctionForm.valid) {
      this.dialog.open(OkDialogComponent, {data: {title: 'Nieuwe veiling', message: 'Gegevens voor een nieuwe veiling zijn niet correct ingevuld'}});
    }
    else {
        this.dialog.open(OkDialogComponent, {data: {title: 'Nieuwe veiling', message: 'Nieuwe veiling is gestart'}});
    }
  }
}

