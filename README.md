# Podarochnaya

Podarochnaya — приложение, предназначенное для создания и управления списками подарков. Сервис позволяет пользователям создавать вишлисты, бронировать подарки, а также просматривать списки других пользователей. Также реализована логика тайного санты на бэке.

## Технологии
- **Backend**: Java 21, Spring Boot, PostgreSQL для хранения данных
- **Хранилище файлов**: MinIO

## Инструкция по сборке и запуску

### Предварительные требования

Для успешного запуска проекта необходимо:
1. **Docker** и **Docker Compose**.
2. Создать файл `.env` в корневой директории проекта с переменными окружения для конфигурации базы данных и MinIO. Пример `.env` файла:

   ```env
   # PostgreSQL
   POSTGRES_DB=wishlist_db
   POSTGRES_USER=wishlist_user
   POSTGRES_PASSWORD=wishlist_password

   # MinIO
   MINIO_ACCESS_KEY=minio_access_key
   MINIO_SECRET_KEY=minio_secret_key

## Сборка и запуск всех сервисов
Откройте терминал в корневой директории проекта и выполните команду:

```docker-compose up --build```

Эта команда создаст и запустит все контейнеры, указанные в docker-compose.yml.

## Проверка состояния сервисов
После запуска, сервисы будут доступны по следующим портам:
- Backend: http://localhost:8080
- Frontend: http://localhost:5173
- MinIO: http://localhost:9000 (логин и пароль заданы в .env файле)
- PostgreSQL: localhost:5432

## Завершение работы
Чтобы остановить, выполните команду:
```docker-compose down```