# Book Management App — Deployment Guide

A Spring Boot + React application for cataloging and managing a personal or classroom book library. This guide covers running the **deployment** build, which uses an embedded SQLite database instead of PostgreSQL, so no external database server is required.

## Prerequisites

| Tool | Version | Notes |
|---|---|---|
| Java (JDK) | 21+ | Required to build and run the Spring Boot backend |
| Node.js | 18+ | Required to build the React frontend |
| npm | Comes with Node.js | Used to install frontend dependencies |

Maven does not need to be installed separately — the project includes the Maven Wrapper (`mvnw` / `mvnw.cmd`).

## 1. Clone the repository

```bash
git clone https://github.com/twagner9/java-book-management-springboot.git
cd java-book-management-springboot
```

## 2. Build the frontend and bundle it into the backend

The React frontend is compiled to static assets and copied into the Spring Boot app's static resources folder, so the backend can serve the UI directly.

```bash
cd frontend
npm install
npm run build-and-copy
```

> On Windows, use `npm run build-and-copy-win` instead.

This compiles the React app with Vite and copies the output into `src/main/resources/static`.

## 3. Build the backend

From the project root:

```bash
cd ..
./mvnw clean package
```

> On Windows, use `mvnw.cmd clean package` instead.

This produces an executable JAR at `target/BookManagementApp-0.0.1-SNAPSHOT.jar`.

## 4. Run the application in deployment mode

The **deployment** profile configures the app to use an embedded SQLite database (`bookmanager.db`), which is created automatically in the working directory on first run — no database setup needed.

**Option A — run the packaged JAR:**

```bash
java -jar target/BookManagementApp-0.0.1-SNAPSHOT.jar --spring.profiles.active=deployment
```

**Option B — run via the Maven wrapper:**

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=deployment
```

## 5. Access the app

Once running, open an additional terminal and execute the frontend:

```
cd frontend
npm run start
```

## Notes

- **Data storage:** In deployment mode, all book data is stored locally in a `bookmanager.db` SQLite file created next to where you run the app. Deleting this file resets the catalog.
- **Other profiles:** A `dev` profile also exists, which connects to a local PostgreSQL database instead of SQLite. This is intended for local development only and requires a running PostgreSQL instance (see `notes_for_myself.txt` in the repo for setup commands).
- **Rebuilding after frontend changes:** If you update the frontend, re-run `npm run build-and-copy` (step 2) and rebuild the backend (step 3) so the changes are included in the packaged app.
