# =============================================================================
# AirWatch - Dockerfile
# Grupo Solsticio | FIAP 2026
# =============================================================================

# ── Stage 1: BUILD ────────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ── Stage 2: RUNTIME ──────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

# ✅ Usuário não-privilegiado (requisito: executar com usuário não root)
RUN addgroup -S solsticio && adduser -S airwatch -G solsticio

# ✅ Diretório de trabalho definido
WORKDIR /app/airwatch

# Copia o JAR gerado no stage de build
COPY --from=build /build/target/*.jar airwatch.jar

# Ajusta permissão para o usuário não-root
RUN chown -R airwatch:solsticio /app/airwatch

# Muda para o usuário não-root
USER airwatch

# ✅ Variável de ambiente
ENV SPRING_PROFILES_ACTIVE=prod \
    APP_NAME=AirWatch \
    APP_VERSION=1.0.0

# ✅ Porta exposta
EXPOSE 8080

ENTRYPOINT ["java", \
  "-Xmx512m", \
  "-Xms256m", \
  "-XX:+UseContainerSupport", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "airwatch.jar"]
