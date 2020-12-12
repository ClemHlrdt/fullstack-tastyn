export class User {
  constructor(
    public username: string,
    public email: string,
    public id: string,
    public token?: string,
    public expirationDate?: Date,
    public avatar?: string,
    public createdAt?: string,
    public description?: string
  ) {}
}
