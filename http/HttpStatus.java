package http;

public enum HttpStatus {
  OK(200, "OK"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error");

  public final int code;
  public final String reason;

  HttpStatus(int c, String r) {
    this.code = c;
    this.reason = r;
  }
}
