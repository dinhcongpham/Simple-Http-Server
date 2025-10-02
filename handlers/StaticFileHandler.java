package handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import pipeline.HttpHandler;

public class StaticFileHandler implements HttpHandler {
  private final Path root;

  public StaticFileHandler(Path root) {
    this.root = root.toAbsolutePath().normalize();
  }

  @Override
  public HttpResponse handle(HttpRequest req) throws IOException {
    String sub = req.pathParams().getOrDefault("path", "");
    Path target = root.resolve(sub).normalize();
    if (!target.startsWith(root) || !Files.exists(target) || Files.isDirectory(target)) {
      return HttpResponse.status(HttpStatus.NOT_FOUND).text("Not found").build();
    }
    String contentType = Files.probeContentType(target);
    if (contentType == null)
      contentType = "application/octet-stream";
    byte[] bytes = Files.readAllBytes(target);
    return HttpResponse.status(HttpStatus.OK).bytes(bytes, contentType).build();
  }
}
