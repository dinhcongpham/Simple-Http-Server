package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import http.HttpRequest;
import http.HttpResponse;
import pipeline.HttpHandler;

public class ConnectionWorker implements Runnable {
  private final Socket socket;
  private final HttpHandler app;

  public ConnectionWorker(Socket socket, HttpHandler app) {
    this.socket = socket;
    this.app = app;
  }

  @Override
  public void run() {
    try (socket;
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream()) {

      HttpRequest request = RequestParser.parse(in);
      HttpResponse response = app.handle(request);
      ResponseWriter.write(out, response);

    } catch (Exception e) {
      try (OutputStream out = socket.getOutputStream()) {
        ResponseWriter.write(out, HttpResponse.internalServerError("Internal Server Error"));
      } catch (IOException ignored) {
      }
    }
  }
}
