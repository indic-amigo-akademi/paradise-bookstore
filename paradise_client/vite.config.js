import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

console.log("Project Dir: ", path.dirname(__dirname));

export default defineConfig({
    base: "/",
    css: {
        preprocessorOptions: {
            scss: {
                api: "modern-compiler" // or "modern"
            }
        }
    },
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
        outDir: path.join(path.dirname(__dirname), "paradise_server", "src", "main", "resources", "dist"),
        rollupOptions: {
            output: {
                manualChunks: (id) => {
                    if (id.includes("@mui")) {
                        return "mui";
                    }
                    if (id.includes("node_modules")) {
                        return "vendor";
                    }
                }
            }
        },
        chunkSizeWarningLimit: 2000
    }
});
