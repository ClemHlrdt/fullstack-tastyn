import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommentsService } from 'src/app/services/comments/comments.service';
import { Comment } from 'src/app/models/comment.model';
import { Authentication } from 'src/app/models/authentication.model';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss'],
})
export class CommentsComponent implements OnInit, OnDestroy {
  routeSub: Subscription;
  authSub: Subscription;
  commentSub: Subscription;
  recipeId: number;
  comments: Comment[];
  currentUser: Authentication;

  constructor(
    private authService: AuthService,
    private commentsService: CommentsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe((params: Params) => {
      this.recipeId = +params.id;
    });

    this.commentSub = this.commentsService.comments.subscribe((comments) => {
      this.comments = comments;
    });

    this.commentsService.loadAll(this.recipeId);

    this.authSub = this.authService.auth.subscribe(
      (auth: Authentication) => (this.currentUser = auth)
    );
  }

  onUpdateComment(comment: Comment) {
    this.commentsService.setCurrentRecipe(comment);
  }

  onDeleteComment(comment: Comment) {
    this.commentsService.removeComment(this.recipeId, comment.id);
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
    if (this.commentSub) {
      this.commentSub.unsubscribe();
    }
    if (this.authSub) {
      this.authSub.unsubscribe();
    }
  }
}
