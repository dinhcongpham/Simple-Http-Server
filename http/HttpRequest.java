package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private final HttpMethod method;
  private final String path;
  private final String version;
  private final Headers headers;
  private final Map<String, String> query;
  private final byte[] body;
  private final Map<String, String> pathParams = new HashMap<>();

  public HttpRequest(HttpMethod method, String path, String version, Headers headers, Map<String, String> query,
      byte[] body) {
    this.method = method;
    this.path = path;
    this.version = version;
    this.headers = headers;
    this.query = query;
    this.body = body == null ? new byte[0] : body;
  }

  public HttpMethod method() {
    return method;
  }

  public String path() {
    return path;
  }

  public String version() {
    return version;
  }

  public Headers headers() {
    return headers;
  }

  public Map<String, String> query() {
    return Collections.unmodifiableMap(query);
  }

  public byte[] body() {
    return body;
  }

  public Map<String, String> pathParams() {
    return pathParams;
  }

  public String query(String key) {
    return query.get(key);
  }
}