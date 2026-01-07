# Santulan – Rate Limiter API 🚦

**Santulan** is a Spring Boot–based API rate-limiting project built using **Bucket4j**.  
It demonstrates how to protect APIs from excessive requests using an **API-key-based rate limiter** implemented as a **Servlet Filter**.

The project is lightweight, interview-ready, and designed for learning as well as real-world understanding.

---

## ✨ Features

- API-key-based request validation
- Rate limiting using Bucket4j (Token Bucket algorithm)
- 10 requests per minute per API key
- Custom response headers:
  - `X-RateLimit-Remaining`
- Automatic blocking with `429 Too Many Requests`
- In-memory, thread-safe implementation
- Filter-based design (no controller pollution)
- Fully testable using Postman

---

## 🧠 How It Works

1. Client sends a request with a custom API key header.
2. The `RateLimitFilter` intercepts all `/api/*` requests.
3. A Bucket4j token bucket is resolved per API key.
4. If tokens are available:
   - Request is allowed
   - Remaining tokens are returned in response headers
5. If tokens are exhausted:
   - Request is blocked with HTTP `429`

---

## 🔑 API Key Details

- **Header Name:** `SIDDHASWARI-111`
- **Header Value:** Any non-empty string (acts as the API key)

Example:
