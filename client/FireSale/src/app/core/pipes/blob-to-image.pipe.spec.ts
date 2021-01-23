import { TestBed } from '@angular/core/testing';
import { BlobToImagePipe } from './blob-to-image.pipe';

describe('BlobToImagePipe', () => {
  let pipe: BlobToImagePipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BlobToImagePipe]
    });
    pipe = TestBed.inject(BlobToImagePipe);
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });
});
