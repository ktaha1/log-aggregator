FROM amazoncorretto:17-alpine

RUN addgroup -S appuser && adduser -S appuser -G appuser

WORKDIR /app

ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -Djava.security.egd=file:/dev/./urandom \
    -Duser.timezone=UTC"

COPY target/log-publisher-*.jar app.jar
RUN chown appuser:appuser /app/app.jar

USER appuser:appuser

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]