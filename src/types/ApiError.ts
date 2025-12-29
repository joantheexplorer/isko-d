export class ApiError extends Error {
  status: number;
  body: any;

  constructor(status: number, body: any) {
    super(body?.message ?? "API Error");
    this.status = status;
    this.body = body;
  }
}
