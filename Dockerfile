
# ==========================================
# 1. BUILD DO FRONTEND
# ==========================================
FROM node:22-alpine AS frontend-build

WORKDIR /frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend/ ./
RUN npm run build


# ==========================================
# 2. BUILD DO BACKEND
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS backend-build

WORKDIR /app

COPY backend/pom.xml .

# Baixa as dependências antes de copiar o código
RUN mvn dependency:go-offline -B

COPY backend/src ./src

# Copia o frontend compilado para os recursos estáticos
COPY --from=frontend-build /frontend/dist ./src/main/resources/static

RUN mvn clean package -DskipTests


# ==========================================
# 3. IMAGEM FINAL
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=backend-build /app/target/*.jar app.jar

EXPOSE 10000

ENV PORT=10000

CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
