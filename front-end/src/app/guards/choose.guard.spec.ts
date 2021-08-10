import { TestBed } from '@angular/core/testing';

import { ChooseGuard } from './choose.guard';

describe('ChooseGuard', () => {
  let guard: ChooseGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ChooseGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
