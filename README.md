# 🌐 Simple HTTP Server (Java)

A lightweight, modular HTTP server built entirely from scratch using **core Java**, with no external frameworks.  
Designed for learning and understanding how real HTTP servers work under the hood — including routing, middleware, and request handling.

---

## 🚀 Features

- ⚙️ **Pure Java Implementation** — No external frameworks or dependencies  
- 🧩 **Modular Architecture** — Includes routing, middleware pipeline, and handlers  
- 📂 **Static & Dynamic Routes** — Serve static files or handle custom logic  
- 🌍 **CORS Support** — Easily enable cross-origin requests  
- 🚨 **Error Handling** — Built-in error and exception middleware  
- 🪵 **Logging** — Simple and configurable request logging  

---

## 🏗️ Core Components

| Component | Description |
|------------|-------------|
| **HttpServer** | Manages client connections and spawns worker threads |
| **HttpRequestParser** | Parses raw HTTP requests into structured objects |
| **HttpResponseWriter** | Writes and sends HTTP responses to clients |
| **Router** | Maps routes (GET, POST, etc.) to corresponding handlers |
| **Middleware** | Allows request/response interception for CORS, logging, etc. |
| **StaticFileHandler** | Serves files from a specified public directory |

---

## 🧠 Learning Highlights

This project was built to deepen understanding of:
- TCP socket programming and connection lifecycle
- HTTP protocol (request parsing, response formatting, headers, methods)
- Thread management for concurrent connections
- Middleware and routing patterns found in modern web frameworks