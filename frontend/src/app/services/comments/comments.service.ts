import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { DataStorageService } from 'src/app/services/data-storage.service';
import { Comment } from '../../models/comment.model';

const emptyComment = {
  id: null,
  comment: '',
  user: null,
};

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private _currentComment = new BehaviorSubject<Comment>(emptyComment);
  private _comments = new BehaviorSubject<Comment[]>([]);
  private dataStore: Comment[] = [];

  constructor(private dataStorageService: DataStorageService) {}

  get comments() {
    return this._comments.asObservable();
  }

  get currentComment() {
    return this._currentComment.asObservable();
  }

  loadAll(id: number) {
    this.dataStorageService
      .getRecipeById(id)
      .pipe(pluck('comments'))
      .subscribe(
        (comments: Comment[]) => {
          this.dataStore = comments;
          this._comments.next(this.dataStore);
        },
        (error) => console.log('Could not load comments')
      );
  }

  addComment(id: number, comment: Comment) {
    this.dataStorageService.addComment(id, comment).subscribe(
      (data) => {
        this.dataStore.push(data);
        this._comments.next(this.dataStore);
        this._currentComment.next(emptyComment);
      },
      (error) => console.log("Couldn't add a new comment")
    );
  }

  setCurrentRecipe(comment: Comment) {
    this._currentComment.next(comment);
  }

  updateComment(recipeId: number, comment: Comment) {
    this.dataStorageService.updateComment(recipeId, comment).subscribe(
      (data) => {
        this.dataStore = this.dataStore.filter((x) => x.id !== comment.id);
        this.dataStore.push(data);
        this._comments.next(this.dataStore);
        this._currentComment.next(emptyComment);
      },
      (error) => console.log("Couldn't update the comment")
    );
  }

  removeComment(recipeId: number, commentId: number) {
    this.dataStorageService.removeComment(recipeId, commentId).subscribe(() => {
      this.dataStore = this.dataStore.filter((x) => x.id !== commentId);
      this._comments.next(this.dataStore);
    });
  }
}
