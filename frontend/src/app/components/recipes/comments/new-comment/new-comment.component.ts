import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommentsService } from 'src/app/services/comments/comments.service';
import { Comment } from 'src/app/models/comment.model';
import { Authentication } from 'src/app/models/authentication.model';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.scss'],
})
export class NewCommentComponent implements OnInit, OnDestroy {
  recipeId: number;
  comment = new FormControl('', Validators.required);
  user: User;
  auth: Authentication;
  commentId: number;
  isUpdate: boolean = false;

  private subscriptions: Subscription[] = [];

  routerSub: Subscription;
  authSub: Subscription;
  commentsSub: Subscription;

  constructor(
    private commentsService: CommentsService,
    private router: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const routerSub = this.router.params.subscribe((params) => {
      this.recipeId = params.id;
    });

    const authSub = this.authService.auth.subscribe((auth: Authentication) => {
      this.auth = auth;
    });

    const commentsSub = this.commentsService.currentComment.subscribe(
      (currentComment: Comment) => {
        if (currentComment.comment !== '') {
          this.isUpdate = true;
          this.commentId = currentComment.id;
        }
        this.comment.setValue(currentComment.comment);
      }
    );

    const user = this.userService.getUserByUserId(this.auth.userId).subscribe(
      (user: User) => {
        this.user = user;
      },
      (err) => console.log(err)
    );

    this.subscriptions.push(routerSub, authSub, commentsSub, user);
  }

  onAddComment() {
    if (this.comment.valid) {
      const comment = new Comment(this.comment.value, this.user);
      this.commentsService.addComment(this.recipeId, comment);
    }
  }

  onUpdateComment() {
    if (this.comment.valid) {
      const comment = new Comment(
        this.comment.value,
        this.user,
        this.commentId
      );
      this.commentsService.updateComment(this.recipeId, comment);
      this.isUpdate = false;
    }
  }

  onCancelComment() {
    this.isUpdate = false;
    this.comment.reset();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
