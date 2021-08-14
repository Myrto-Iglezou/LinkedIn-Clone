import { TestBed } from '@angular/core/testing';

import { CreatePostService } from './post.service';

describe('CreatePostService', () => {
  let service: CreatePostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreatePostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
