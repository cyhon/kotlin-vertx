FROM docker.finogeeks.club/base/jre:9-slim

WORKDIR /app

ADD config config
ADD build/libs/PROJECT_NAME-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory", "-jar", "app.jar"]
