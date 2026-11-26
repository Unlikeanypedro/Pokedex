# ==========================================================================
# ETAPA 1 - BUILD
# Imagem com Maven 3.9 + JDK 17 para compilar o projeto e gerar o .jar
# ==========================================================================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Reaproveita as dependências e resolve apenas o que o build precisa.
RUN --mount=type=cache,target=/root/.m2/repository mvn -B package -DskipTests

# ==========================================================================
# ETAPA 2 - EXECUÇÃO
# Imagem menor, só com o Java 17 (JRE), para rodar o .jar
# ==========================================================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Mesma porta do application.properties (server.port=8080)
EXPOSE 8080

# MaxRAMPercentage: usa até 75% da memória do container
# (o plano gratuito do Render tem 512 MB)
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
