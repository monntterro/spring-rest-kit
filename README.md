# Spring REST Kit

Легковесный каркас для быстрого создания REST API приложений на Spring Boot с архитектурой
Controller–Service–Repository–Mapper.

## Описание

Spring REST Kit предоставляет базовую структуру для реализации CRUD-операций с минимальным количеством шаблонного кода.
Библиотека использует generic-типы и единые интерфейсы, стандартизируя REST-endpoint'ы.

### Основные компоненты

* **CrudKit** — абстрактный контроллер с реализацией CRUD-операций.
* **ServiceKit** — базовый сервисный слой.
* **RepositoryKit** — интерфейс репозитория для работы с данными.
* **MapperKit** — интерфейс маппера для преобразования Entity ↔ Model.

### Поддерживаемые HTTP-методы

* `GET /{id}` — получение одного объекта
* `GET /` — получение списка
* `POST /` — создание объекта
* `PUT /{id}` — обновление объекта
* `DELETE /{id}` — удаление объекта

## Примеры использования

### 1. Entity

```java

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Integer age;
}
```

### 2. DTO (Model)

```java
public class UserModel {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
```

### 3. MapperKit

Можно реализовать вручную или использовать MapStruct — сигнатуры методов совместимы.

```java

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends MapperKit<UserEntity, UserModel> {

    UserEntity toEntity(UserModel model);

    UserModel toModel(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity updateWithNull(@MappingTarget UserEntity target, UserModel model);
}
```

### 4. RepositoryKit

Обычно достаточно расширить `JpaRepository` и добавить `RepositoryKit`.

```java
public interface UserRepository
        extends JpaRepository<UserEntity, Long>, RepositoryKit<UserEntity, Long> {
}
```

### 5. ServiceKit

```java

@Service
public class UserService extends ServiceKit<UserEntity, Long, UserModel> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}
```

### 6. Controller

```java

@RestController
@RequestMapping("/api/users")
public class UserController extends CrudKit<UserEntity, Long, UserModel> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
```

## Примеры API

### Создание пользователя

```bash
POST /api/users
Content-Type: application/json
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}
```

### Получение всех

```bash
GET /api/users
```

### Получение по ID

```bash
GET /api/users/1
```

### Обновление

```bash
PUT /api/users/1
Content-Type: application/json
{
  "name": "John Smith",
  "email": "john.smith@example.com"
}
```

### Удаление

```bash
DELETE /api/users/1
```

## Преимущества

* Минимум шаблонного кода
* Стандартизированная структура REST API
* Type-safe благодаря generics
* Простое расширение бизнес-логики
* Соответствие принципам SOLID
* Готовые аннотации Spring

## Требования

* Java 17+
* Spring Boot 3.x
* Spring Web