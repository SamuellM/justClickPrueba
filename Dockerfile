FROM openjdk:8-jdk-buster
MAINTAINER samuelmoreira@hotmail.es
COPY *.jar app.jar
RUN rm /etc/localtime && \
ln -s /usr/share/zoneinfo/America/Guayaquil /etc/localtime && \
mkdir -p app/log && \
mkdir -p app/config &&\
chmod 777 app
EXPOSE 2036
ENTRYPOINT ["java","-XX:+UseG1GC","-Dfile.encoding=UTF-8","-Duser.timezone=America/guayaquil","-jar","/app.jar","--spring.config.location=file:/app/config/application.properties"]

