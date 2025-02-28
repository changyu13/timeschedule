# base-image
FROM openjdk:8-jdk-alpine

# 변수설정 (빌드파일의 경로)
ARG JAR_FILE=target/*.jar

# 빌드파일을 컨테이너로 복사
COPY ${JAR_FILE} app.jar

# jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]