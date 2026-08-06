# 1단계: 프로젝트 빌드 (포장)
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 빌드에 필요한 파일들 복사 (.kts 반영)
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# 권한 부여 후 빌드 (테스트 과정 생략하여 빌드 속도 단축)
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

# 2단계: 실제 실행 환경
FROM eclipse-temurin:21-jre
WORKDIR /app

# 1단계에서 성공적으로 빌드된 jar 파일만 복사해오기
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 개방 및 서버 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]