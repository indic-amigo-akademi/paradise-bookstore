import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

console.log("Project Dir: ", path.dirname(__dirname));

export default defineConfig({
    base: "/",
    define: {
        "process.env.PUBLIC_URL": JSON.stringify(
            process.env.NODE_ENV === "production" ? "https://paradise-store.com/" : "http://localhost:5050/"
        ),
        "process.env.NODE_ENV": JSON.stringify(process.env.NODE_ENV)
    },
    plugins: [react({})],
    resolve: {
        extensions: [".js", ".jsx", ".ts", ".tsx", ".json"],
        alias: {
            "@": path.resolve("src")
        }
    },
    build: {
        outDir: path.join(path.dirname(__dirname),"paradise_server", "dist")
    }
});
