import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

export default defineConfig({
    base: "/",
    define: {
        "process.env.PUBLIC_URL": JSON.stringify(
            process.env.NODE_ENV === "production" ? "https://paradise-store.com/" : "http://localhost:8080/"
        ),
        "process.env.NODE_ENV": JSON.stringify(process.env.NODE_ENV)
    },
    plugins: [react({})],
    resolve: {
        extensions: [".js", ".jsx", ".ts", ".tsx", ".json"],
        alias: {
            "@": path.resolve("src")
        }
    }
});
