FROM maven:3-openjdk-11 as BUILD_IMAGE
ENV APP_HOME=/root/dev/myapp/
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME
COPY . .
RUN mvn -B package -e -X --file pom.xml -DskipTests

FROM openjdk:11
WORKDIR /root/
COPY --from=BUILD_IMAGE /root/dev/myapp/target/comicstoreapi*.jar .
CMD java -jar comicstoreapi*.jar

