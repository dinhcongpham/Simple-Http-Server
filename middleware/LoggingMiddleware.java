package middleware;

import http.HttpRequest;
import http.HttpResponse;
import pipeline.HttpHandler;
import pipeline.Middleware;

public class LoggingMiddleware implements Middleware {
  @Override
  public HttpHandler wrap(HttpHandler next) {
    return (HttpRequest req) -> {
      long t0 = System.nanoTime();
      HttpResponse res = next.handle(req);
      long dt = (System.nanoTime() - t0) / 1_000_000;
      System.out.printf("%s %s -> %d (%d ms)%n", req.method(), req.path(), res.status().code, dt);
      return res;
    };
  }
}
