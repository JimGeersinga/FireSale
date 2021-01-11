import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ImageDTO } from '../../models/imageDto';

@Component({
  selector: 'app-image-viewer-dialog',
  templateUrl: './image-viewer-dialog.component.html',
  styleUrls: ['./image-viewer-dialog.component.scss']
})
export class ImageViewerDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: { images: ImageDTO[], imageIndex: number }) { }

  ngOnInit(): void {
  }

}
