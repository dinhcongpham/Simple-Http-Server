package http;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
  private final HttpStatus status;
  private final Headers headers;
  private final byte[] body;

  private HttpResponse(HttpStatus status, Headers headers, byte[] body) {
    this.status = status;
    this.headers = headers;
    this.body = body;
  }

  public static Builder status(HttpStatus s) {
    return new Builder(s);
  }

  public static HttpResponse ok(String text) {
    return status(HttpStatus.OK).text(text).build();
  }

  public static HttpResponse notFound(String msg) {
    return status(HttpStatus.NOT_FOUND).text(msg).build();
  }

  public static HttpResponse internalServerError(String msg) {
    return status(HttpStatus.INTERNAL_SERVER_ERROR).text(msg).build();
  }

  public HttpStatus status() {
    return status;
  }

  public Headers headers() {
    return headers;
  }

  public byte[] body() {
    return body;
  }

  public static class Builder {
    private final HttpStatus status;
    private final Headers headers = new Headers();
    private byte[] body = new byte[0];

    public Builder(HttpStatus status) {
      this.status = status;
    }

    public Builder header(String k, String v) {
      headers.put(k, v);
      return this;
    }

    public Builder text(String text) {
      header("Content-Type", "text/plain; charset=utf-8");
      this.body = text.getBytes(StandardCharsets.UTF_8);
      return this;
    }

    public Builder bytes(byte[] bytes, String contentType) {
      header("Content-Type", contentType);
      this.body = bytes;
      return this;
    }

    public HttpResponse build() {
      return new HttpResponse(status, headers, body);
    }
  }
}
