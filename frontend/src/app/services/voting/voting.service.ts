import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Recipe } from 'src/app/models/recipe.model';
import { Vote } from 'src/app/models/vote.model';
import { DataStorageService } from '../data-storage.service';
import { RecipeService } from '../recipe/recipe.service';

@Injectable({
  providedIn: 'root',
})
export class VotingService {
  voteSubject: BehaviorSubject<number> = new BehaviorSubject<number>(null);
  voteObservable: Observable<number> = this.voteSubject.asObservable();

  userVote: Subject<Vote> = new Subject<Vote>();
  userVoteObservable: Observable<Vote> = this.userVote.asObservable();

  constructor(private dataStorageService: DataStorageService) {}

  loadVotes(votes: number) {
    this.voteSubject.next(votes);
  }

  loadUserVote(recipeId: number) {
    this.dataStorageService
      .getVoteUserVoteForRecipe(recipeId)
      .subscribe((vote: Vote) => {
        this.userVote.next(vote);
      });
  }

  upVoteRecipe(recipe: Recipe) {
    this.dataStorageService
      .voteForRecipe(1, recipe.id)
      .subscribe((recipe: Recipe) => {
        this.voteSubject.next(recipe.votes);
        this.loadUserVote(recipe.id);
      });
  }

  downVoteRecipe(recipe: Recipe) {
    this.dataStorageService
      .voteForRecipe(-1, recipe.id)
      .subscribe((recipe: Recipe) => {
        this.voteSubject.next(recipe.votes);
        this.loadUserVote(recipe.id);
      });
  }
}
