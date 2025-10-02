package http;

public enum HttpMethod {
  GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD;

  public static HttpMethod from(String s) {
    return HttpMethod.valueOf(s.toUpperCase());
  }
}