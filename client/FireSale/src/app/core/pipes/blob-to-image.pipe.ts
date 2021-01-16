import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'blobToImage'
})
export class BlobToImagePipe implements PipeTransform {

  constructor(protected sanitizer: DomSanitizer) { }

  transform(value: unknown, ...args: unknown[]): unknown {
    if (!value) { return 'assets/images/NoImage.png'; }
    return this.sanitizer.bypassSecurityTrustUrl('data:image/jpg;base64, ' + value);
  }

}
