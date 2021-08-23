import { TestBed } from '@angular/core/testing';

import { SkillsExperienceService } from './skills-experience.service';

describe('SkillsExperienceService', () => {
  let service: SkillsExperienceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SkillsExperienceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
