import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsinfeedComponent } from './postsinfeed.component';

describe('PostsinfeedComponent', () => {
  let component: PostsinfeedComponent;
  let fixture: ComponentFixture<PostsinfeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostsinfeedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostsinfeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
