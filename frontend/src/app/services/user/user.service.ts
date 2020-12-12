import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { map } from 'rxjs/operators';
import { User } from 'src/app/models/user.model';
import { environment } from 'src/environments/environment';
import { DataStorageService } from '../data-storage.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userSubject = new Subject<User>();
  user: Observable<User> = this.userSubject.asObservable();

  constructor(
    private http: HttpClient,
    private dataStorageService: DataStorageService
  ) {}

  getUserByUserId(userId: string) {
    return this.dataStorageService.getUserById(userId);
  }

  updateUser(user: User, updatedUser: User) {
    return this.dataStorageService.updateUser(user.id, updatedUser);
  }

  uploadImage(user: User, image: File) {
    const formData = new FormData();
    formData.append('file', image);

    return this.http.post(`/api/users/${user.id}/upload`, formData);
  }
}
