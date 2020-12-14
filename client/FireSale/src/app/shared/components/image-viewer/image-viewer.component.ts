import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss']
})
export class ImageViewerComponent implements OnInit {

  @Input() public enableHoverPreview = false;
  @Input() public images: string[];

  public currentImageIndex = 0;

  constructor() { }

  ngOnInit(): void {
    this.images = [
      'https://loremflickr.com/450/300?lock=1',
      'https://loremflickr.com/450/300?lock=2',
      'https://loremflickr.com/450/450?lock=3',
      'https://loremflickr.com/300/450?lock=4',
      'https://loremflickr.com/550/300?lock=5',
      'https://loremflickr.com/450/300?lock=6',
      'https://loremflickr.com/450/300?lock=7',
      'https://loremflickr.com/450/300?lock=8',
      'https://loremflickr.com/450/300?lock=9',
      'https://loremflickr.com/450/300?lock=10'
    ];
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
    document.getElementById('image-' + this.currentImageIndex).scrollIntoView();
  }
}
