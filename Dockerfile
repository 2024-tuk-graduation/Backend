

FROM openjdk:17 AS builder

# 패키지를 설치해야, bootJAR 를 실행할 수 있다
RUN microdnf install findutils

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJAR


FROM openjdk:17
# 위 builder에서 만든 .jar파일을 컨테이너 내부로 복사
COPY --from=builder build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh", "mysqldb:3306", "-s", "-t", "100", "--"]

