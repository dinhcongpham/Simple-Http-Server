package core;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import http.Headers;
import http.HttpMethod;
import http.HttpRequest;

public final class RequestParser {
    private RequestParser() {
    }

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(in);
        ByteArrayOutputStream headBuf = new ByteArrayOutputStream();
        int prev = -1, curr;
        // Read header bytes until \r\n\r\n
        while ((curr = bin.read()) != -1) {
            headBuf.write(curr);
            if (prev == '\r' && curr == '\n') {
                byte[] hb = headBuf.toByteArray();
                int len = hb.length;
                if (len >= 4 && hb[len - 4] == '\r' && hb[len - 3] == '\n' && hb[len - 2] == '\r'
                        && hb[len - 1] == '\n') {
                    break;
                }
            }
            prev = curr;
        }
        String headersPart = headBuf.toString(StandardCharsets.US_ASCII);
        String[] lines = headersPart.split("\r\n");
        if (lines.length == 0)
            throw new IOException("Bad request");

        String[] start = lines[0].split(" ");
        if (start.length < 3)
            throw new IOException("Bad request line");
        HttpMethod method = HttpMethod.from(start[0]);
        String target = start[1]; // path?query
        String version = start[2];

        Headers headers = new Headers();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isEmpty())
                break;
            int idx = lines[i].indexOf(':');
            if (idx > 0)
                headers.add(lines[i].substring(0, idx).trim(), lines[i].substring(idx + 1).trim());
        }

        String path = target;
        Map<String, String> query = new HashMap<>();
        int qidx = target.indexOf('?');
        if (qidx >= 0) {
            path = target.substring(0, qidx);
            String q = target.substring(qidx + 1);
            for (String kv : q.split("&")) {
                if (kv.isEmpty())
                    continue;
                String[] p = kv.split("=", 2);
                String k = urlDecode(p[0]);
                String v = p.length > 1 ? urlDecode(p[1]) : "";
                query.put(k, v);
            }
        }

        byte[] body = new byte[0];
        int contentLength = headers.getInt("Content-Length", 0);
        if (contentLength > 0) {
            body = bin.readNBytes(contentLength);
        }

        return new HttpRequest(method, path, version, headers, query, body);
    }

    private static String urlDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
}
