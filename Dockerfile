# Этап сборки
FROM maven:3.8.6-eclipse-temurin-17 as builder
WORKDIR /opt/app

# Копируем все файлы проекта и исходники
COPY mvnw pom.xml ./
COPY ./src ./src

# Выполняем сборку проекта (без тестов)
RUN mvn clean package -DskipTests

# Финальный образ с JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080



# Копируем собранный JAR-файл из этапа сборки
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar

# Копируем init-db.sh, который будет запускаться перед запуском приложения

# Запуск приложения и добавление данных в базу данных

CMD ["java", "-jar", "/opt/app/app.jar"]
