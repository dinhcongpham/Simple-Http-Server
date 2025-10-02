package middleware;

import http.HttpMethod;
import http.HttpResponse;
import http.HttpStatus;
import pipeline.HttpHandler;
import pipeline.Middleware;

public class CorsMiddleware implements Middleware {
    @Override public HttpHandler wrap(HttpHandler next) {
        return req -> {
            if (req.method() == HttpMethod.OPTIONS) {
                return HttpResponse.status(HttpStatus.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS")
                        .header("Access-Control-Allow-Headers", "Content-Type,Authorization")
                        .build();
            }
            var res = next.handle(req);
            res.headers().putIfAbsent("Access-Control-Allow-Origin", "*");
            return res;
        };
    }
}
