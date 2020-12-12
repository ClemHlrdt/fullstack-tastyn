import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Recipe } from 'src/app/models/recipe.model';
import { VotingService } from '../../../../services/voting/voting.service';
import { Vote } from '../../../../models/vote.model';
@Component({
  selector: 'app-recipe-vote',
  templateUrl: './recipe-vote.component.html',
  styleUrls: ['./recipe-vote.component.scss'],
})
export class RecipeVoteComponent implements OnInit, OnDestroy {
  @Input()
  recipe: Recipe;

  votes: number;

  userVote: Vote;

  subscriptions: Subscription[] = [];

  constructor(private votingService: VotingService) {}

  ngOnInit(): void {
    const votesSub = this.votingService.voteObservable.subscribe(
      (votes: number) => {
        this.votes = votes;
      }
    );

    const voteUserSub = this.votingService.userVoteObservable.subscribe(
      (vote: Vote) => {
        this.userVote = vote;
      }
    );

    this.votingService.loadVotes(this.recipe.votes);
    this.votingService.loadUserVote(this.recipe.id);

    this.subscriptions.push(votesSub, voteUserSub);
  }

  handleUpvote() {
    this.votingService.upVoteRecipe(this.recipe);
  }

  handleDownVote() {
    this.votingService.downVoteRecipe(this.recipe);
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
