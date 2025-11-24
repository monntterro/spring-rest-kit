# 🚀 Spring REST Kit

Легковесный каркас для быстрого создания REST приложений на Spring Boot с модульной архитектурой
Controller–Service–Repository–Mapper.

## 📦 Описание

Spring REST Kit предоставляет гибкую структуру для реализации REST API с минимальным количеством шаблонного кода.
Библиотека использует generic-типы и модульные интерфейсы, позволяя выборочно подключать нужные HTTP-методы.

### 🛠 Основные компоненты

#### Базовые интерфейсы и классы

* **RestKit** — базовый интерфейс, предоставляющий доступ к сервису и мапперу
* **AbstractRestController** — абстрактный контроллер, реализующий `RestKit`
* **ServiceKit** — базовый сервисный слой с готовой бизнес-логикой
* **RepositoryKit** — интерфейс репозитория для работы с данными
* **MapperKit** — интерфейс маппера для преобразования Entity ↔ Model

#### Модульные HTTP-методы

Каждый HTTP-метод представлен отдельным интерфейсом, что позволяет гибко комбинировать функциональность:

* **GetOne** — `GET /{id}` — получение одного объекта
* **GetAll** — `GET /` — получение списка объектов
* **PostOne** — `POST /` — создание объекта
* **PutOne** — `PUT /{id}` — обновление объекта
* **DeleteOne** — `DELETE /{id}` — удаление объекта

#### Готовые наборы

* **CrudKit** — полный набор CRUD-операций (композиция всех интерфейсов выше)

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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends MapperKit<UserEntity, UserModel> {

    UserEntity toEntity(UserModel model);

    UserModel toModel(UserEntity entity);

    UserEntity updateWithNull(@MappingTarget UserEntity target, UserModel model);
}
```

### 4. RepositoryKit

Обычно достаточно расширить `JpaRepository` и добавить `RepositoryKit`.

```java
public interface UserRepository extends JpaRepository<UserEntity, Long>, RepositoryKit<UserEntity, Long> {
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

#### Вариант A: Полный CRUD с CrudKit

```java
@RestController
@RequestMapping("/api/users")
public class UserController extends CrudKit<UserEntity, Long, UserModel> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
```

#### Вариант B: Кастомная комбинация методов

```java

@RestController
@RequestMapping("/api/users")
public class CustomUserController extends AbstractRestController<UserEntity, Long, UserModel>
        implements GetAll<UserEntity, Long, UserModel>,
        PostOne<UserEntity, Long, UserModel>,
        PutOne<UserEntity, Long, UserModel> {

    public CustomUserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }

    // Можно добавлять собственные методы
    @GetMapping("/search")
    public Collection<UserModel> searchByName(@RequestParam String name) {
        // Кастомная логика
    }
}
```

## 💡 Преимущества

* **Модульность** — выбирайте только нужные HTTP-методы
* **Минимум шаблонного кода** — готовая реализация стандартных операций
* **Type-safe** — безопасность типов благодаря generics
* **Гибкость** — легко комбинировать встроенные и кастомные методы
* **Расширяемость** — простое добавление собственной бизнес-логики
* **Стандартизация** — единообразная структура REST API
* **SOLID-принципы** — чистая архитектура с разделением ответственности
* **Spring-ready** — готовые аннотации Spring из коробки

## 🎯 Архитектурные решения

### Модульный подход

Библиотека следует принципу композиции интерфейсов. Каждый HTTP-метод — это отдельный интерфейс с default-реализацией.
Это даёт:

* Возможность создавать read-only контроллеры
* Гибкость в выборе доступных операций
* Простоту расширения функциональности

### Разделение ответственности

* **Controller** — обработка HTTP-запросов и преобразование Model
* **Service** — бизнес-логика и работа с Entity
* **Repository** — доступ к данным
* **Mapper** — преобразование Entity <-> Model

## 📌 Требования

_Сама библиотека не несет в себе зависимости_

* Java 17+
* Spring Boot 3.x
* Spring Web
* Spring Data JPA (для использования с JpaRepository)

## 🔧 Расширение функциональности

Вы можете переопределять методы по умолчанию или добавлять собственные:

```java

@RestController
@RequestMapping("/api/users")
public class EnhancedUserController extends CrudKit<UserEntity, Long, UserModel> {

    public EnhancedUserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }

    // Переопределение стандартного метода
    @Override
    public Collection<UserModel> getAll() {
        // Своя логика, например, с сортировкой
        return getService().getAll().stream()
                .sorted(Comparator.comparing(UserEntity::getName))
                .map(getMapper()::toModel)
                .toList();
    }

    // Добавление нового эндпоинта
    @GetMapping("/active")
    public Collection<UserModel> getActiveUsers() {
        // Кастомная логика
    }
}
```