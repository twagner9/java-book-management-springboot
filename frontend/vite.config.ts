import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  build: {
    // This outputs code to your old 'build' folder
    // so your existing copy script still works perfectly
    outDir: "build",
    emptyOutDir: true,
  },
  server: {
    port: 5173, // Matches your old local development port
  },
});
