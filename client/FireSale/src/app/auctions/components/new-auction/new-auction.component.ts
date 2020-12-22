import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { OkDialogComponent } from 'src/app/shared/components/ok-dialog/ok-dialog.component';
import { AuctionService } from '../../shared/auction.service';
import { CategoryDTO } from '../../../shared/models/categoryDto';
import { CategoryService } from '../../../shared/services/category.service';
import { CreateAuctionDTO } from '../../models/createAuctionDto';
import { BehaviorSubject, Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { map, switchMap } from 'rxjs/operators';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER, TAB } from '@angular/cdk/keycodes';
import { TagService } from 'src/app/shared/services/tag.service';
import { TagDto } from '../../../shared/models/tagDto';

@Component({
  selector: 'app-new-auction',
  templateUrl: './new-auction.component.html',
  styleUrls: ['./new-auction.component.scss'],
})
export class NewAuctionComponent implements OnInit {

  public selectedFiles = [];
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA, TAB];
  tagsControl = new FormControl();
  tags: TagDto[] = [];
  currentTags: string[] = [];

  public searchBehaviourSubject$ = new BehaviorSubject("");
  public allTags$: Observable<string[]>;

  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  public newAuctionForm = this.formBuilder.group({
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

  add(event: MatChipInputEvent): void {
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

  remove(tag: string): void {
    const index = this.currentTags.indexOf(tag);

    if (index >= 0) {
      this.currentTags.splice(index, 1);
    }
  }

  autocomplete(event) {
    const searchTerm = event.target.value;
    this.searchBehaviourSubject$.next(searchTerm);
  };

  selected(event: MatAutocompleteSelectedEvent): void {
    this.currentTags.push(event.option.viewValue);
    console.log(event);
    this.tagInput.nativeElement.value = '';
    this.tagsControl.setValue(null);
  }

  ngOnInit(): void {
    this.categoryService.get().subscribe(response => this.categories = response.data);
    this.allTags$ = this.searchBehaviourSubject$.pipe(
      switchMap(searchTerm => {
        return this.tagService.searchTagsByName(searchTerm)
      }),
      map(data => {
        return data;
      })
    );
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
    data.tags = [];
    this.currentTags.map(tag => {data.tags.push({name:tag})});
      if (data.endDate < data.startDate) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Einddatum moet voorbij de startdatum liggen.' } });
    } else if (data.minimalBid < 1) {
      this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Wij nemen aan dat je wel een minimumbod wil hebben, dus vul hiervoor een bedrag in.' } });
    } else {
      if (!this.newAuctionForm.valid) {
        this.dialog.open(OkDialogComponent, { data: { title: 'Nieuwe veiling', message: 'Gegevens voor een nieuwe veiling zijn niet correct ingevuld' } });
      }
      else {
        data.images = [];
        this.selectedFiles.forEach((element) => {
          const encodedImage = element.split('base64,')[1];
          const fileExtension = "." + element.split(';')[0].split('/')[1];
          data.images.push({
            imageB64: encodedImage,
            type: fileExtension,
            sort: 0, // Moet nog aanpasbaar worden in UI
          });
        });
        console.log(data);
        this.auctionService.post(data).subscribe(result => {
          this.router.navigate(['/auctions/details', result.data.id]);
        });
      }
    }
  }
}
