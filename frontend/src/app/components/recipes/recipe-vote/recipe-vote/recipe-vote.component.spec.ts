import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeVoteComponent } from './recipe-vote.component';

describe('RecipeVoteComponent', () => {
  let component: RecipeVoteComponent;
  let fixture: ComponentFixture<RecipeVoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeVoteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeVoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
