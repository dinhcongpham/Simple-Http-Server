package routing;

import http.HttpMethod;
import pipeline.HttpHandler;

public record Route(HttpMethod method, PathTemplate template, HttpHandler handler) {
}