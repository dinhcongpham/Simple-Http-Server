package pipeline;

import http.HttpRequest;
import http.HttpResponse;

@FunctionalInterface
public interface HttpHandler {
  HttpResponse handle(HttpRequest request) throws Exception;
}