package pipeline;

import java.util.ArrayList;
import java.util.List;

public final class Pipeline {
  private final List<Middleware> middlewares = new ArrayList<>();
  private HttpHandler terminal;

  private Pipeline(HttpHandler terminal) {
    this.terminal = terminal;
  }

  public static Pipeline start(HttpHandler terminal) {
    return new Pipeline(terminal);
  }

  public Pipeline through(Middleware m) {
    middlewares.add(m);
    return this;
  }

  public HttpHandler build() {
    HttpHandler h = terminal;
    for (Middleware m : middlewares)
      h = m.wrap(h); // last added wraps innermost
    return h;
  }
}