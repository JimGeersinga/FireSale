import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ImageDTO } from 'src/app/shared/models/imageDto';
import { ImageViewerDialogComponent } from '../image-viewer-dialog/image-viewer-dialog.component';

@Component({
  selector: 'app-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss']
})
export class ImageViewerComponent implements OnInit {

  @Input() public currentImageIndex = 0;
  @Input() public enableImageExpansion = true;
  @Input() public enableHoverPreview = false;
  @Input() public images: ImageDTO[];


  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  public showImage(index: number): void {
    this.currentImageIndex = index;
    this.scrollIntoView();
  }

  public nextImage(): void {
    this.currentImageIndex = (this.currentImageIndex + 1) % this.images.length;
    this.scrollIntoView();
  }
  public prevImage(): void {
    if (this.currentImageIndex - 1 < 0) {
      this.currentImageIndex = this.images.length - 1;
    } else {
      this.currentImageIndex--;
    }
    this.scrollIntoView();
  }

  public expandImage(): void {
    if (!this.enableImageExpansion) { return; }
    this.dialog.open(ImageViewerDialogComponent, { data: { images: this.images, imageIndex: this.currentImageIndex }, height: '90vh', width: '90vw' });
  }

  private scrollIntoView(): void {
    document.getElementById('image-' + this.currentImageIndex)?.scrollIntoView();
  }
}
