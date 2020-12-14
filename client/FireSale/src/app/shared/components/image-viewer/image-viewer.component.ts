import { Component, Input, OnInit } from '@angular/core';
import { ImageDTO } from 'src/app/auctions/models/imageDTO';

@Component({
  selector: 'app-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss']
})
export class ImageViewerComponent implements OnInit {

  @Input() public enableHoverPreview = false;
  @Input() public images: ImageDTO[];

  public currentImageIndex = 0;

  constructor() { }

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

  private scrollIntoView(): void {
    document.getElementById('image-' + this.currentImageIndex)?.scrollIntoView();
  }
}
