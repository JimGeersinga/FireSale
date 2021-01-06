import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { BehaviorSubject, Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { map, switchMap } from 'rxjs/operators';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER, TAB } from '@angular/cdk/keycodes';
import { TagService } from 'src/app/shared/services/tag.service';
import { TagDto } from 'src/app/shared/models/tagDto';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { CreateAuctionDTO } from 'src/app/shared/models/createAuctionDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
  selector: 'app-auction-edit',
  templateUrl: './auction-edit.component.html',
  styleUrls: ['./auction-edit.component.scss'],
})
export class AuctionEditComponent implements OnInit {

  selectedFiles = [];
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA, TAB];
  tagsControl = new FormControl();
  tags: TagDto[] = [];
  currentTags: string[] = [];

  public searchBehaviourSubject$ = new BehaviorSubject('');
  public allTags$: Observable<string[]>;

  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  public auctionEditForm = this.formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    minimalBid: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    categories: ['', Validators.required],
    tags: [[]]
  });

  public minDate: Date = new Date();
  public categories: CategoryDTO[];

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private auctionService: AuctionService,
    private router: Router,
    private categoryService: CategoryService,
    private tagService: TagService,
  ) { }

  ngOnInit(): void {
    this.categoryService.get().subscribe(response => this.categories = response.data);
    this.allTags$ = this.searchBehaviourSubject$.pipe(
      switchMap(searchTerm => {
        return this.tagService.searchTagsByName(searchTerm);
      }),
      map(data => {
        return data;
      })
    );
  }

  public addTag(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add tag
    if ((value || '').trim()) {
      this.currentTags.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.tagsControl.setValue(null);
  }

  public removeTag(tag: string): void {
    const index = this.currentTags.indexOf(tag);

    if (index >= 0) {
      this.currentTags.splice(index, 1);
    }
  }

  public autocompleteTag(event): void {
    const searchTerm = event.target.value;
    this.searchBehaviourSubject$.next(searchTerm);
  }

  public selectedTags(event: MatAutocompleteSelectedEvent): void {
    this.currentTags.push(event.option.viewValue);
    console.log(event);
    this.tagInput.nativeElement.value = '';
    this.tagsControl.setValue(null);
  }

  public selectFiles(event): void {
    if (event.target.files && event.target.files[0]) {
      const filesAmount = event.target.files.length;
      for (let i = 0; i < filesAmount; i++) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.selectedFiles.push(e.target.result); // voorbeeld weergevan op pagina
        };
        reader.readAsDataURL(event.target.files[i]);
      }
    }
  }

  public submitAuction(data: CreateAuctionDTO): void {
    if (!this.auctionEditForm.valid) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Gegevens voor een nieuwe veiling zijn niet correct ingevuld' } });
    }
    else {
      data.tags = [];
      this.currentTags.map(tag => { data.tags.push({ name: tag }); });

      data.images = [];
      this.selectedFiles.forEach((element) => {
        const encodedImage = element.split('base64,')[1];
        const fileExtension = '.' + element.split(';')[0].split('/')[1];
        data.images.push({
          imageB64: encodedImage,
          type: fileExtension,
          sort: 0, // Moet nog aanpasbaar worden in UI
        });
      });

      this.auctionService.post(data).subscribe(result => {
        this.router.navigate(['/auctions/details', result.data.id]);
      });
    }
  }
}
