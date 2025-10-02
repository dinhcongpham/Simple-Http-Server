package middleware;

import http.HttpResponse;
import http.HttpStatus;
import pipeline.HttpHandler;
import pipeline.Middleware;

public class ErrorHandlingMiddleware implements Middleware {
  @Override
  public HttpHandler wrap(HttpHandler next) {
    return req -> {
      try {
        return next.handle(req);
      } catch (Exception e) {
        System.err.println("[MiniSrv] Unhandled: " + e);
        return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .text("Internal Server Error").build();
      }
    };
  }
}
