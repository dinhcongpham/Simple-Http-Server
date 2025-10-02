package pipeline;

public interface Middleware {
  HttpHandler wrap(HttpHandler next);
}