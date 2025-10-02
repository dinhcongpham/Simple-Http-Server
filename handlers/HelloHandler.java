package handlers;

import http.HttpRequest;
import http.HttpResponse;
import pipeline.HttpHandler;

public class HelloHandler implements HttpHandler {
  private final String greeting;

  public HelloHandler(String greeting) {
    this.greeting = greeting;
  }

  @Override
  public HttpResponse handle(HttpRequest req) {
    String name = req.pathParams().getOrDefault("name", "world");
    return HttpResponse.ok(greeting + ", " + name + "!");
  }
}
