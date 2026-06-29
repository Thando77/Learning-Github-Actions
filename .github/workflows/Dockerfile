# Use a lightweight Java runtime
FROM eclipse-temurin:21-jre
MAINTAINER Thandolwethu Mdishwa <tmdishwa6@gmail.com>

# Set working directory
WORKDIR /app

# JVM options (can be overridden at runtime)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Copy the application JAR
COPY target/*.jar app.jar

# Create non-root user for security
RUN useradd -m appuser
USER appuser

# Expose application port
EXPOSE 8000

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]