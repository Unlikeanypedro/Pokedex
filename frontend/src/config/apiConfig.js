const API_CONFIG = {
    // Os serviços já incluem /api no caminho. Uma base vazia usa a mesma origem.
    BASE_URL: (import.meta.env.VITE_API_URL ?? "").trim().replace(/\/+$/, ""),
};

export default API_CONFIG;
