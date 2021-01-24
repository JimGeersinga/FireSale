import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
} from '@angular/material/autocomplete';
import { map, switchMap } from 'rxjs/operators';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER, TAB } from '@angular/cdk/keycodes';
import { TagService } from 'src/app/shared/services/tag.service';
import { TagDTO } from 'src/app/shared/models/tagDto';
import { CategoryDTO } from 'src/app/shared/models/categoryDto';
import { CreateAuctionDTO } from 'src/app/shared/models/createAuctionDto';
import { AuctionService } from 'src/app/shared/services/auction.service';
import { CategoryService } from 'src/app/shared/services/category.service';
import { ApiResponse } from 'src/app/core/services/apiResponse';
import { AuctionDTO } from 'src/app/shared/models/auctionDto';
import { CreateImageDTO } from 'src/app/shared/models/createImageDto';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-auction-edit',
  templateUrl: './auction-edit.component.html',
  styleUrls: ['./auction-edit.component.scss'],
})
export class AuctionEditComponent implements OnInit {
  private id: number;
  public model$: Observable<ApiResponse<AuctionDTO>>;
  edit = false;
  selectedCategories: number[];
  pagetitle = 'Maak een nieuwe veiling aan';
  submitbuttontext = 'Start veiling';
  auctionIsRunning = true;

  selectedFiles: CreateImageDTO[] = [];
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA, TAB];
  tagsControl = new FormControl();
  tags: TagDTO[] = [];
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
    tags: [[]],
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
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer
  ) {
    this.route.params.subscribe((params) => {
      if (params.id) {
        this.id = params.id;
        this.model$ = this.auctionService.getSingle(this.id);
        this.edit = true;
        this.submitbuttontext = 'Wijzigingen opslaan';
      }
    });
  }

  private buildForm(auction?: AuctionDTO): void {
    this.auctionEditForm = this.formBuilder.group({
      name: [auction?.name],
      description: [auction?.description],
      categories: [auction?.categories.map((x) => x.id)],
      tags: [auction?.tags.map((x) => x.name)],
      minimalBid: [auction?.minimalBid],
      startDate: [auction?.startDate],
      endDate: [auction?.endDate],
      images: [auction?.images],
    });
    if (this.edit) {
      this.pagetitle = `Wijzig veiling ${auction.name}`;
    }
    this.currentTags = auction?.tags.map((x) => x.name) || [];
    this.selectedFiles = auction?.images || [];
    if (Date.parse(auction?.startDate.toString()) > Date.now()) {
      this.auctionIsRunning = false;
    }
  }

  ngOnInit(): void {
    this.categoryService
      .get()
      .subscribe((response) => (this.categories = response.data));

    this.allTags$ = this.searchBehaviourSubject$.pipe(
      switchMap((searchTerm) => {
        return this.tagService.searchTagsByName(searchTerm);
      }),
      map((data) => {
        return data.data.map(x => x.name);
      })
    );

    this.route.params.subscribe((params) => {
      this.id = params?.id;
      if (this.id) {
        this.auctionService.getSingle(this.id).subscribe((auction) => {
          this.buildForm(auction.data);
        });
      } else {
        this.buildForm();
      }
    });
  }

  public addTag(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.currentTags.push(value.trim());
    }

    input.value = '';

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
    for (const file of event.target.files) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const encodedImage = e.target.result.split('base64,')[1];
        const fileExtension = '.' + e.target.result.split(';')[0].split('/')[1];
        this.selectedFiles.push({
          id: null,
          path: encodedImage,
          type: fileExtension,
          sort: 0,
        }); // voorbeeld weergevan op pagina
      };

      reader.readAsDataURL(file);
    }
  }

  public removeFile(index): void {
    this.selectedFiles.splice(index, 1);
  }

  sanitize(url: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  public submitAuction(data: CreateAuctionDTO): void {
    if (!this.auctionEditForm.valid) {
      this.dialog.open(
        OkDialogComponent, {
        data: this.edit
          ? {
            title: 'Wijzig veiling',
            message: 'Gegevens voor het wijzigen van de veiling zijn niet correct ingevuld',
          }
          : {
            title: 'Nieuwe veiling',
            message: 'Gegevens voor een nieuwe veiling zijn niet correct ingevuld',
          }
      });
    } else {
      data.tags = [];
      this.currentTags.forEach((tag) => {
        data.tags.push({ name: tag });
      });

      data.images = this.selectedFiles;

      if (this.edit) {
        this.auctionService.put(this.id, data).subscribe((result) => {
          this.router.navigate(['/auctions', this.id]);
        });
      } else {
        this.auctionService.post(data).subscribe((result) => {
          this.router.navigate(['/auctions', result.data.id]);
        });
      }
    }
  }
}
