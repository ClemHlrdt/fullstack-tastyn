import { User } from './user.model';

export class Comment {
  constructor(public comment: string, public user?: User, public id?: number) {}
}
