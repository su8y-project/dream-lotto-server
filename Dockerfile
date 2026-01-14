# 1단계: 빌드 스테이지
FROM gradle:8.5.0-jdk21 AS build
WORKDIR /app

# 전체 소스 코드 복사 및 실행 JAR 빌드
COPY . .
RUN ./gradlew :bootstrap:bootJar -x test

# 2단계: 실행 스테이지 (이미지 최적화)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 헬스체크를 위한 curl 설치 (alpine 기반이므로 필요)
RUN apk add --no-cache curl

# 빌드 스테이지에서 생성된 JAR만 복사 (bootstrap 모듈 기준)
COPY --from=build /app/bootstrap/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]