# 🚀 Spring REST Kit

Легковесный каркас для быстрого создания REST приложений на Spring Boot с модульной архитектурой
Controller–Service–Repository–Mapper.

## 📦 Описание

Spring REST Kit предоставляет гибкую структуру для реализации REST API с минимальным количеством шаблонного кода.
Библиотека использует generic-типы и модульные интерфейсы, позволяя выборочно подключать нужные HTTP-методы.

### 🛠 Основные компоненты

> Для подробного руководства по использованию, основным компонентам и примеров кода смотреть
> в [документации](.github/docs/DOCUMENTATION.md)

#### Контроллеры

* **AbstractKitController<TEntity, TId, TDto>** — базовый абстрактный класс для контроллеров без пагинации.

* **CrudKitController<TEntity, TId, TDto>** — готовый абстрактный класс с полным набором CRUD-операций, расширяет
  `AbstractKitController` и реализует все интерфейсы HTTP-методов.

#### Сервис

* **AbstractKitService<TEntity, TId, TDto>** — базовый абстрактный класс сервисного слоя. Содержит готовую реализацию
  бизнес-логики для CRUD-операций, работает с Entity.

#### Репозиторий

* **BaseKitRepository<TEntity, TId>** — интерфейс репозитория, определяет контракт для работы с данными. Совместим с
  JpaRepository.

#### Маппер

* **BaseKitMapper<TEntity, TDto>** — интерфейс маппера для преобразования между Entity и DTO.

#### Обработчик ошибок

* **BaseKitExceptionHandler** — централизованная обработка ошибок `ApiException` и валидации `@Valid`

Библиотека предоставляет два варианта архитектуры:

1. **Без пагинации**: `CrudKitController` + `AbstractKitService` + `BaseKitRepository`
2. **С пагинацией**: `CrudKitPageableController` + `AbstractKitPageableService` + `BaseKitPageableRepository`

### ✨ Пример CRUD контроллера

```java

@Tag(name = "Пользователи")
@RestController
@RequestMapping("/api/users")
public class UserController extends CrudKitController<UserEntity, Long, UserDto> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}

// Service
@Service
public class UserService extends AbstractKitService<UserEntity, Long, UserDto> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}

// Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, BaseKitRepository<UserEntity, Long> {
}

// Mapper
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseKitMapper<UserEntity, UserDto> {

    UserEntity toEntity(UserDto dto);

    UserDto toDto(UserEntity entity);

    UserEntity updateWithNull(@MappingTarget UserEntity target, UserDto dto);
}

// Exception handler
@RestControllerAdvice
public class GlobalExceptionHandler implements BaseKitExceptionHandler {
}
```

### ✨ Пример контроллера с пагинацией

Изменить только контроллер, сервис и репозиторий на Pageable версии:

```java

@Tag(name = "Заказы")
@RestController
@RequestMapping("/api/orders")
public class OrderController extends CrudKitPageableController<OrderEntity, Long, OrderDto> {

    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
    }
}

// Service
@Service
public class OrderService extends AbstractKitPageableService<OrderEntity, Long, OrderDto> {

    public OrderService(OrderRepository repository, OrderMapper mapper) {
        super(repository, mapper);
    }
}

// Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, BaseKitPageableRepository<OrderEntity, Long> {
}
```

## 🏗 Использование через Maven Local

1. Убедитесь, что артефакт установлен в локальный Maven-репозиторий командой:

```bash
./gradlew publishToMavenLocal --no-configuration-cache
```

2. В проекте, где хотите использовать библиотеку, добавьте:

```gradle
repositories {
    mavenLocal()
}
```

3. Подключите зависимость:

```gradle
dependencies {
    implementation 'space.monntterro:spring-rest-kit:1.0.0'
}
```

## 💡 Преимущества

* SOLID-принципы — чистая архитектура с разделением ответственности
* Модульность — выбирайте только нужные HTTP-методы
* Swagger/OpenAPI — автоматическая документация API
* Встроенная валидация — автоматическая обработка `@Valid` с детальными сообщениями об ошибках
* Минимум шаблонного кода — готовая реализация стандартных операций
* Поддержка пагинации — встроенная работа с Spring Data Pageable
* Type-safe — безопасность типов благодаря generics
* Гибкость — легко комбинировать встроенные и кастомные методы
* Расширяемость — простое добавление собственной бизнес-логики
* Стандартизация — единообразная структура REST API
* Spring-ready — готовые аннотации Spring из коробки

## 🎯 Архитектурные решения

### Модульный подход

Библиотека следует принципу композиции интерфейсов. Каждый HTTP-метод — это отдельный интерфейс с default-реализацией.
Это даёт:

* Возможность создавать read-only контроллеры
* Гибкость в выборе доступных операций
* Простоту расширения функциональности
* Выбор между обычной выборкой и пагинацией

### Разделение ответственности

* **Controller** — обработка HTTP-запросов и преобразование DTO
* **Service** — бизнес-логика и работа с Entity
* **Repository** — доступ к данным
* **Mapper** — преобразование Entity <-> DTO

## 📌 Требования

_Сама библиотека не несет в себе зависимости._

* Java 17+
* Spring Boot 3.x
* Spring Web
* Spring Data JPA (для использования с JpaRepository)
* Spring Validation (для использования `@Valid`)
* SpringDoc OpenAPI (опционально, для Swagger-документации)