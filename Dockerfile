# Используйте openjdk:21-ea-20-jdk-buster в качестве базового образа
FROM openjdk:21-ea-20-jdk-buster

# Установка рабочей директории
WORKDIR /app

# Копирование JAR-файла в контейнер
COPY target/softlink_logistic-0.0.1-SNAPSHOT.jar app.jar

# Команда запуска приложения
CMD ["java", "-jar", "app.jar"]

# Замените 'your-app.jar' на имя вашего собранного JAR-файла
