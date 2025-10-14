# Todo Backend (Spring Boot)

Simple Java Spring Boot To-Do REST API intended for deployment on Render.

## Features
- REST endpoints for Todos
- JPA persistence (H2 for local dev, Postgres in production)
- Health endpoint
- CORS enabled (change allowed origins in production)

## Endpoints
- `GET /api/todos` — list todos
- `POST /api/todos` — create a todo (JSON: `{ "title": "...", "completed": false }`)
- `DELETE /api/todos/{id}` — delete todo
- `GET /health` — health check (200 OK)

## Local dev (no Docker)
1. Build:
java -jar target/todo-backend-1.0.0.jar
3. Default config uses H2 (in-memory). Open `http://localhost:8080/h2-console` if needed.

## Docker (recommended for Render)
Build and run locally:
docker build -t todo-backend .
docker run -p 8080:8080 todo-backend
## Deploy to Render
1. Push this repository to GitHub.
2. In Render dashboard -> New -> Web Service -> Connect repo -> Select branch.
3. Choose **Docker** environment (Render will use the included `Dockerfile`).
4. Set environment variables (if using Postgres):
   - `SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<db>`
   - `SPRING_DATASOURCE_USERNAME=...`
   - `SPRING_DATASOURCE_PASSWORD=...`
   - Or use Render's Postgres add-on and it populates `DATABASE_URL` — set `SPRING_DATASOURCE_URL` accordingly.
5. Deploy. The service will run on port 8080.

## Environment configuration
Default `application.properties` uses H2. For production with Postgres, set:
SPRING_DATASOURCE_URL=jdbc:postgresql://:5432/
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_JPA_HIBERNATE_DDL_AUTO=update
## Notes
- Secure CORS for production: change `WebMvcConfig`.
- Switch from in-memory to Postgres for persistence.# todo-backend
