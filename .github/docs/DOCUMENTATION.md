## 🛠 Основные компоненты

#### Готовые наборы

* **CrudKitController<TEntity, TId, TDto>** — готовый абстрактный класс контроллера с полным набором CRUD-операций.
  Расширяет `AbstractKitController` и реализует все интерфейсы HTTP-методов (GetOne, GetAll, PostOne, PutOne,
  DeleteOne).

#### Интерфейсы

* **BaseKitRestController<TEntity, TId, TDto>** — базовый интерфейс контроллера, предоставляет доступ к
  `AbstractKitService` и
  `BaseKitMapper`. Используется как основа для всех REST-контроллеров. Содержит **базовое Swagger описание** методов
  CRUD.
  Для уточнения, какая именно сущность используется, рекомендуется указывать `@Tag` над контроллером.

* **BaseKitRepository<TEntity, TId>** — интерфейс репозитория, определяет контракт для работы с данными (findById,
  findAll,
  save, deleteById). Совместим с JpaRepository.

* **BaseKitMapper<TEntity, TDto>** — интерфейс маппера для преобразования между Entity и DTO. Определяет методы:
  `toDto()`,
  `toEntity()`, `updateWithNull()`.

#### Абстрактные классы

* **AbstractKitController<TEntity, TId, TDto>** — базовый абстрактный класс для контроллеров. Реализует
  `BaseKitRestController`, хранит ссылки на service и mapper. Предоставляет методы `getService()` и `getMapper()`.
* **AbstractKitService<TEntity, TId, TDto>** — базовый абстрактный класс сервисного слоя. Содержит готовую реализацию
  бизнес-логики для CRUD-операций: getOne, getAll, createOne, updateOne, deleteOne. Работает с Entity.

#### Модульные HTTP-методы (интерфейсы)

Каждый HTTP-метод представлен отдельным интерфейсом с default-реализацией. Все расширяют
`BaseKitRestController<TEntity, TId, TDto>`:

* **GetOne<TEntity, TId, TDto>** — `GET /{id}` — получение одного объекта по ID. Возвращает DTO или throws exception
  если не найден.
* **GetAll<TEntity, TId, TDto>** — `GET /` — получение списка всех объектов. Возвращает Collection<TDto>.
* **PostOne<TEntity, TId, TDto>** — `POST /` — создание нового объекта. Принимает DTO в RequestBody, возвращает
  созданный DTO.
* **PutOne<TEntity, TId, TDto>** — `PUT /{id}` — обновление существующего объекта. Принимает ID и DTO, возвращает
  обновлённый DTO.
* **DeleteOne<TEntity, TId, TDto>** — `DELETE /{id}` — удаление объекта по ID. Возвращает удалённый DTO если объект был
  найден, иначе выбрасывает ошибку.

## ✨ Примеры использования

### 1. Создать Entity

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

### 2. Создать DTO

```java
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
```

### 3. Создать Mapper

Можно реализовать вручную или использовать MapStruct — сигнатуры методов совместимы.

```java

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseKitMapper<UserEntity, UserDto> {

    UserEntity toEntity(UserDto dto);

    UserDto toDto(UserEntity entity);

    UserEntity updateWithNull(@MappingTarget UserEntity target, UserDto dto);
}
```

### 4. Создать Repository

Обычно достаточно расширить JpaRepository и добавить BaseKitRepository.

```java
public interface UserRepository extends JpaRepository<UserEntity, Long>, BaseKitRepository<UserEntity, Long> {
}
```

### 5. Создать Service

```java

@Service
public class UserService extends AbstractKitService<UserEntity, Long, UserDto> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}
```

### 6. Создать Controller

#### Полный CRUD с CrudKitController

```java

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи")
public class UserController extends CrudKitController<UserEntity, Long, UserDto> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
```

#### Кастомная комбинация методов

```java

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи")
public class CustomUserController extends AbstractKitController<UserEntity, Long, UserDto> implements
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

## 🔧 Расширение функциональности

Вы можете переопределять методы по умолчанию или добавлять собственные:

```java

@RestController
@RequestMapping("/api/users")
public class EnhancedUserController extends CrudKitController<UserEntity, Long, UserDto> {

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