import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [
    react(),
    {
      name: "suppress-malformed-uri",
      configureServer(server) {
        server.middlewares.use((req, res, next) => {
          try {
            decodeURI(req.url ?? "");
            next();
          } catch {
            res.writeHead(400);
            res.end("Bad Request");
          }
        });
      },
    },
  ],
  build: {
    // This outputs code to your old 'build' folder
    // so your existing copy script still works perfectly
    outDir: "build",
    emptyOutDir: true,
  },
  server: {
    port: 5173,
    proxy: {
      // Ensures that the Spring Boot backend /api will be properly sought by vite, which has its port as 5173
      "/api": "http://localhost:8080",
    },
    middlewareMode: false,
  },
});
