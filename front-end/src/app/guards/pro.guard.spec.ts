import { TestBed } from '@angular/core/testing';

import { HostGuard } from './pro.guard';

describe('HostGuard', () => {
  let guard: HostGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(HostGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
