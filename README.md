# 🚀 Spring REST Kit

Легковесный каркас для быстрого создания REST приложений на Spring Boot с модульной архитектурой
Controller–Service–Repository–Mapper.

## 📦 Описание

Spring REST Kit предоставляет гибкую структуру для реализации REST API с минимальным количеством шаблонного кода.
Библиотека использует generic-типы и модульные интерфейсы, позволяя выборочно подключать нужные HTTP-методы.

### 🛠 Основные компоненты

#### Интерфейсы

* **BaseRestController<TEntity, TId, TDto>** — базовый интерфейс контроллера, предоставляет доступ к `ServiceKit` и
  `BaseMapper`. Используется как основа для всех REST-контроллеров.

* **BaseRepository<TEntity, TId>** — интерфейс репозитория, определяет контракт для работы с данными (findById, findAll,
  save, deleteById). Совместим с JpaRepository.

* **BaseMapper<TEntity, TDto>** — интерфейс маппера для преобразования между Entity и DTO. Определяет методы: `toDto()`,
  `toEntity()`, `updateWithNull()`.

#### Модульные HTTP-методы (интерфейсы)

Каждый HTTP-метод представлен отдельным интерфейсом с default-реализацией. Все расширяют
`BaseRestController<TEntity, TId, TDto>`:

* **GetOne<TEntity, TId, TDto>** — `GET /{id}` — получение одного объекта по ID. Возвращает DTO или throws exception
  если не найден.

* **GetAll<TEntity, TId, TDto>** — `GET /` — получение списка всех объектов. Возвращает Collection<TDto>.

* **PostOne<TEntity, TId, TDto>** — `POST /` — создание нового объекта. Принимает DTO в RequestBody, возвращает
  созданный DTO.

* **PutOne<TEntity, TId, TDto>** — `PUT /{id}` — обновление существующего объекта. Принимает ID и DTO, возвращает
  обновлённый DTO.

* **DeleteOne<TEntity, TId, TDto>** — `DELETE /{id}` — удаление объекта по ID. Возвращает удалённый DTO если объект был
  найден, иначе выбрасывает ошибку.

#### Абстрактные классы

* **AbstractControllerKit<TEntity, TId, TDto>** — базовый абстрактный класс для контроллеров. Реализует
  `BaseRestController`, хранит ссылки на service и mapper. Предоставляет методы `getService()` и `getMapper()`.

* **AbstractServiceKit<TEntity, TId, TDto>** — базовый абстрактный класс сервисного слоя. Содержит готовую реализацию
  бизнес-логики для CRUD-операций: getOne, getAll, createOne, updateOne, deleteOne. Работает с Entity.

#### Готовые наборы

* **CrudControllerKit<TEntity, TId, TDto>** — готовый абстрактный класс контроллера с полным набором CRUD-операций.
  Расширяет `AbstractControllerKit` и реализует все интерфейсы HTTP-методов (GetOne, GetAll, PostOne, PutOne,
  DeleteOne).

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

Теперь библиотека доступна для использования в вашем проекте.

## ✨ Примеры использования

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

### 2. DTO

```java
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
```

### 3. BaseMapper

Можно реализовать вручную или использовать MapStruct — сигнатуры методов совместимы.

```java

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<UserEntity, UserDto> {

    UserEntity toEntity(UserDto dto);

    UserDto toDto(UserEntity entity);

    UserEntity updateWithNull(@MappingTarget UserEntity target, UserDto dto);
}
```

### 4. BaseRepository

Обычно достаточно расширить `JpaRepository` и добавить `BaseRepository`.

```java
public interface UserRepository extends JpaRepository<UserEntity, Long>, BaseRepository<UserEntity, Long> {
}
```

### 5. AbstractServiceKit

```java

@Service
public class UserService extends AbstractServiceKit<UserEntity, Long, UserDto> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}
```

### 6. Controller

#### Вариант A: Полный CRUD с CrudControllerKit

```java

@RestController
@RequestMapping("/api/users")
public class UserController extends CrudControllerKit<UserEntity, Long, UserDto> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
```

#### Вариант B: Кастомная комбинация методов

```java
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class CustomUserController extends AbstractControllerKit<UserEntity, Long, UserDto> implements
        GetAll<UserEntity, Long, UserDto>,
        PostOne<UserEntity, Long, UserDto>,
        PutOne<UserEntity, Long, UserDto> {

    public CustomUserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }

    @GetMapping("/search")
    public Collection<UserDto> searchByName(@RequestParam String name) {
        // Кастомная логика
    }
}
```

## 💡 Преимущества

* Модульность — выбирайте только нужные HTTP-методы
* Минимум шаблонного кода — готовая реализация стандартных операций
* Type-safe — безопасность типов благодаря generics
* Гибкость — легко комбинировать встроенные и кастомные методы
* Расширяемость — простое добавление собственной бизнес-логики
* Стандартизация — единообразная структура REST API
* SOLID-принципы — чистая архитектура с разделением ответственности
* Spring-ready — готовые аннотации Spring из коробки

## 🎯 Архитектурные решения

### Модульный подход

Библиотека следует принципу композиции интерфейсов. Каждый HTTP-метод — это отдельный интерфейс с default-реализацией.
Это даёт:

* Возможность создавать read-only контроллеры
* Гибкость в выборе доступных операций
* Простоту расширения функциональности

### Разделение ответственности

* Controller — обработка HTTP-запросов и преобразование DTO
* Service — бизнес-логика и работа с Entity
* Repository — доступ к данным
* Mapper — преобразование Entity <-> DTO

## 📌 Требования

Сама библиотека не несет в себе зависимости.

* Java 17+
* Spring Boot 3.x
* Spring Web
* Spring Data JPA (для использования с JpaRepository)

## 🔧 Расширение функциональности

Вы можете переопределять методы по умолчанию или добавлять собственные:

```java

@RestController
@RequestMapping("/api/users")
public class EnhancedUserController extends CrudControllerKit<UserEntity, Long, UserDto> {

    public EnhancedUserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }

    @Override
    public Collection<UserDto> getAll() {
        return getService().getAll().stream()
                .sorted(Comparator.comparing(UserEntity::getName))
                .map(getMapper()::toDto)
                .toList();
    }

    @GetMapping("/active")
    public Collection<UserDto> getActiveUsers() {
        // Кастомная логика
    }
}
```
