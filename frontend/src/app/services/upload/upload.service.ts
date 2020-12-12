import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  constructor(private http: HttpClient) {}

  upload(user: User, formData) {
    return this.http.post<User>(`/api/users/${user.id}/upload`, formData);
  }
}
