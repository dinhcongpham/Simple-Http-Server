package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import http.HttpResponse;

public final class ResponseWriter {
    private ResponseWriter() {
    }

    public static void write(OutputStream out, HttpResponse resp) throws IOException {
        String statusLine = "HTTP/1.1 " + resp.status().code + " " + resp.status().reason + "\r\n";
        out.write(statusLine.getBytes(StandardCharsets.US_ASCII));

        if (!resp.headers().containsKey("Content-Length")) {
            resp.headers().put("Content-Length", String.valueOf(resp.body().length));
        }
        if (!resp.headers().containsKey("Connection")) {
            resp.headers().put("Connection", "close");
        }
        for (var e : resp.headers().entrySet()) {
            out.write((e.getKey() + ": " + e.getValue() + "\r\n").getBytes(StandardCharsets.US_ASCII));
        }
        out.write("\r\n".getBytes(StandardCharsets.US_ASCII));
        out.write(resp.body());
        out.flush();
    }
}
