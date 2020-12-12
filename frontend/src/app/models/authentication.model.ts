export class Authentication {
  constructor(
    public expiration: number,
    public issuedAt: number,
    public email: string,
    public userId: string,
    public token: string
  ) {}
}
