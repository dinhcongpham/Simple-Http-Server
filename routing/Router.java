package routing;

import java.util.ArrayList;
import java.util.List;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import pipeline.HttpHandler;

public class Router implements HttpHandler {
  private final List<Route> routes = new ArrayList<>();

  public Router get(String path, HttpHandler h) {
    return add(HttpMethod.GET, path, h);
  }

  public Router post(String path, HttpHandler h) {
    return add(HttpMethod.POST, path, h);
  }

  public Router put(String path, HttpHandler h) {
    return add(HttpMethod.PUT, path, h);
  }

  public Router delete(String path, HttpHandler h) {
    return add(HttpMethod.DELETE, path, h);
  }

  public Router add(HttpMethod m, String t, HttpHandler h) {
    routes.add(new Route(m, new PathTemplate(t), h));
    return this;
  }

  @Override
  public HttpResponse handle(HttpRequest req) throws Exception {
    for (Route r : routes) {
      if (r.method() != req.method())
        continue;
      var params = r.template().match(req.path());
      if (params != null) {
        req.pathParams().putAll(params);
        return r.handler().handle(req);
      }
    }
    return HttpResponse.notFound("No route for " + req.method() + " " + req.path());
  }
}
