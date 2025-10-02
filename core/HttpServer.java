package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pipeline.HttpHandler;

public class HttpServer {
  private final int port;
  private final HttpHandler app;
  private final ExecutorService pool;
  private volatile boolean running = true;

  public HttpServer(int port, HttpHandler app) {
    this.port = port;
    this.app = app;
    this.pool = new ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors(),
        Math.max(32, Runtime.getRuntime().availableProcessors() * 2),
        60L, TimeUnit.SECONDS, new SynchronousQueue<>(),
        r -> {
          Thread t = new Thread(r, "minisrv-worker");
          t.setDaemon(true);
          return t;
        });
  }

  public void start() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("[MiniSrv] Listening on http://localhost:" + port);
      Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
      while (running) {
        Socket socket = serverSocket.accept();
        socket.setSoTimeout(10_000);
        pool.execute(new ConnectionWorker(socket, app));
      }
    }
  }

  public void shutdown() {
    running = false;
    pool.shutdown();
    try {
      if (!pool.awaitTermination(5, TimeUnit.SECONDS))
        pool.shutdownNow();
    } catch (InterruptedException e) {
      pool.shutdownNow();
      Thread.currentThread().interrupt();
    }
    System.out.println("[MiniSrv] Shutdown complete.");
  }
}
