import java.nio.file.Path;

import core.HttpServer;
import handlers.HelloHandler;
import handlers.StaticFileHandler;
import middleware.CorsMiddleware;
import middleware.ErrorHandlingMiddleware;
import middleware.LoggingMiddleware;
import pipeline.HttpHandler;
import pipeline.Pipeline;
import routing.Router;

public class App {
  public static void main(String[] args) throws Exception {
    Router router = new Router()
        .get("/", new HelloHandler("MiniSrv OK"))
        .get("/hello/{name}", new HelloHandler("Hello"))
        .get("/assets/{*path}", new StaticFileHandler(Path.of("public")));

    HttpHandler app = Pipeline
        .start(router) // terminal handler (Router)
        .through(new CorsMiddleware()) // optional
        .through(new LoggingMiddleware())
        .through(new ErrorHandlingMiddleware()) // should be last added, wraps others
        .build();

    HttpServer server = new HttpServer(8080, app);
    server.start(); // Ctrl+C to stop
  }
}
