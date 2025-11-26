# 📚 Документация Spring REST Kit

## 🛠 Основные компоненты

### Готовые контроллеры

* **CrudKitController<TEntity, TId, TDto>** — полный набор CRUD-операций (GetOne, GetAll, PostOne, PutOne, DeleteOne)
* **CrudKitPageableController<TEntity, TId, TDto>** — CRUD с пагинацией (GetOne, GetAllPageable, PostOne, PutOne,
  DeleteOne)

### Базовые классы

* **AbstractKitController<TEntity, TId, TDto>** — базовый класс для контроллеров без пагинации
* **AbstractKitPageableController<TEntity, TId, TDto>** — базовый класс для контроллеров с пагинацией
* **AbstractKitService<TEntity, TId, TDto>** — готовая реализация CRUD-логики
* **AbstractKitPageableService<TEntity, TId, TDto>** — сервис с пагинацией

### Интерфейсы

* **BaseKitRepository<TEntity, TId>** — контракт репозитория (findById, findAll, save, deleteById)
* **BaseKitPageableRepository<TEntity, TId>** — репозиторий с методом findAll(Pageable)
* **BaseKitMapper<TEntity, TDto>** — маппер (toDto, toEntity, updateWithNull)

### Обработка ошибок

#### BaseKitExceptionHandler

Интерфейс для централизованной обработки исключений. Нужно создать класс, реализующий этот интерфейс, и
пометить его аннотацией `@RestControllerAdvice`.

**Возможности:**

- Содержит готовый обработчик `handleHttpServerErrorException(ApiException e)` для всех ApiException
- Содержит готовый обработчик `handleValidationExceptions(MethodArgumentNotValidException e)` для валидации `@Valid`
- Можно переопределить стандартные обработчики
- Можно добавить свои обработчики для других исключений

**Пример:**

```java

@RestControllerAdvice
public class GlobalExceptionHandler implements BaseKitExceptionHandler {
    // handleHttpServerErrorException и handleValidationExceptions уже реализованы в интерфейсе

    // Можно переопределить стандартный обработчик ApiException
    @Override
    public ProblemDetail handleHttpServerErrorException(ApiException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatusCode());
        problemDetail.setTitle("Кастомный заголовок");
        problemDetail.setDetail(e.getReason());
        return problemDetail;
    }

    // Можно переопределить стандартный обработчик валидации
    @Override
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Ошибка валидации данных");

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    // Или добавить свои обработчики
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
```

#### Встроенная валидация

Библиотека автоматически обрабатывает валидацию `@Valid` в методах `PostOne` и `PutOne`. При ошибке валидации
возвращается RFC 7807 Problem Details с детальной информацией о каждом невалидном поле.

**Пример DTO с валидацией:**

```java
public class UserDto {
    private Long id;

    @NotBlank(message = "Имя обязательно для заполнения")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Email должен быть корректным")
    private String email;

    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    private Integer age;
}
```

**Пример ответа при ошибке валидации:**

```json
{
  "type": "about:blank",
  "title": "Validation failed",
  "status": 400,
  "errors": {
    "name": "Имя обязательно для заполнения",
    "email": "Email должен быть корректным",
    "age": "Возраст должен быть не менее 18 лет"
  }
}
```

#### ApiException

Базовый класс для всех REST-ошибок. Все исключения, наследующие `ApiException`, **автоматически обрабатываются** методом
`handleHttpServerErrorException` из `BaseKitExceptionHandler`.

**Возможности:**

- Наследуется от `ResponseStatusException`
- Принимает `HttpStatus` и сообщение об ошибке
- Автоматически преобразуется в RFC 7807 Problem Details

**Встроенные исключения:**

- `NotFoundException` — 404 ошибка с автоматическим формированием сообщения

**Создание своих исключений:**

```java
public class ValidationException extends ApiException {
    public ValidationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

public class ForbiddenException extends ApiException {
    public ForbiddenException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }
}
```

Все созданные исключения будут автоматически обрабатываться `BaseKitExceptionHandler`.

### Модульные HTTP-методы

Интерфейсы с default-реализацией для выборочного подключения:

* **GetOne<TEntity, TId, TDto>** — `GET /{id}`
* **GetAll<TEntity, TId, TDto>** — `GET /` (без пагинации)
* **GetAllPageable<TEntity, TId, TDto>** — `GET /` (с пагинацией)
* **PostOne<TEntity, TId, TDto>** — `POST /` (с автоматической валидацией `@Valid`)
* **PutOne<TEntity, TId, TDto>** — `PUT /{id}` (с автоматической валидацией `@Valid`)
* **DeleteOne<TEntity, TId, TDto>** — `DELETE /{id}`

## ✨ Примеры использования

### Полный CRUD без пагинации

```java
// 1. Entity
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}

// 2. DTO с валидацией
public class UserDto {
    private Long id;

    @NotBlank(message = "Имя обязательно для заполнения")
    private String name;

    @Email(message = "Email должен быть корректным")
    private String email;
}

// 3. Mapper
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseKitMapper<UserEntity, UserDto> {
    UserEntity toEntity(UserDto dto);
    UserDto toDto(UserEntity entity);
    UserEntity updateWithNull(@MappingTarget UserEntity target, UserDto dto);
}

// 4. Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>,
        BaseKitRepository<UserEntity, Long> {
}

// 5. Service
@Service
public class UserService extends AbstractKitService<UserEntity, Long, UserDto> {
    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}

// 6. Controller
@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи")
public class UserController extends CrudKitController<UserEntity, Long, UserDto> {
    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
```

### Полный CRUD с пагинацией

```java
// Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>,
        BaseKitPageableRepository<OrderEntity, Long> {
}

// Service
@Service
public class OrderService extends AbstractKitPageableService<OrderEntity, Long, OrderDto> {
    public OrderService(OrderRepository repository, OrderMapper mapper) {
        super(repository, mapper);
    }
}

// Controller
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Заказы")
public class OrderController extends CrudKitPageableController<OrderEntity, Long, OrderDto> {
    public OrderController(OrderService service, OrderMapper mapper) {
        super(service, mapper);
    }
}
```

### Кастомная комбинация методов

```java

@RestController
@RequestMapping("/api/products")
@Tag(name = "Товары")
public class ProductController extends AbstractKitController<ProductEntity, Long, ProductDto>
        implements GetAll<ProductEntity, Long, ProductDto>,
        GetOne<ProductEntity, Long, ProductDto> {

    public ProductController(ProductService service, ProductMapper mapper) {
        super(service, mapper);
    }

    @GetMapping("/search")
    public Collection<ProductDto> search(@RequestParam String query) {
        // Кастомная логика
    }
}
```

### Обработка исключений

```java

@RestControllerAdvice
public class GlobalExceptionHandler implements BaseKitExceptionHandler {
    // handleHttpServerErrorException и handleValidationExceptions уже реализованы в интерфейсе
    // Все ApiException и ошибки валидации автоматически обрабатываются

    // Добавляем обработку других исключений при необходимости
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Data Integrity Error");
        problemDetail.setDetail("Нарушение целостности данных");
        return problemDetail;
    }
}
```

### Создание кастомных исключений

```java
// Создаём своё исключение
public class InsufficientBalanceException extends ApiException {
    public InsufficientBalanceException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

// Используем в сервисе
@Service
public class PaymentService extends AbstractKitService<PaymentEntity, Long, PaymentDto> {

    public PaymentEntity processPayment(Long userId, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Недостаточно средств на счёте");
        }
        // логика
    }
}

// Исключение автоматически обработается в GlobalExceptionHandler
```

## 🔧 Расширение функциональности

### Переопределение методов

```java
@RestController
@RequestMapping("/api/users")
public class UserController extends CrudKitController<UserEntity, Long, UserDto> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }

    @Override
    public Collection<UserDto> getAll() {
        return getService().getAll().stream()
                .sorted(Comparator.comparing(UserEntity::getName))
                .map(getMapper()::toDto)
                .toList();
    }
}
```

### Добавление кастомной бизнес-логики

```java

@Service
public class UserService extends AbstractKitService<UserEntity, Long, UserDto> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public TEntity createOne(TEntity entity) {
        validateEmail(entity.getEmail());
        return super.createOne(entity);
    }

    public Collection<UserEntity> findByEmail(String email) {
        // Кастомная логика
    }
}
```

## 📝 Замечания и рекомендации

1. **Обработчик исключений** — создайте класс с `@RestControllerAdvice`, реализующий `BaseKitExceptionHandler`
2. **Валидация** — используйте аннотации `@Valid`, `@NotNull`, `@NotBlank`, `@Email` и другие в DTO для автоматической
   валидации
3. **Используйте ApiException** для своих ошибок — они автоматически обработаются
4. **NotFoundException** автоматически выбрасывается в AbstractKitService при отсутствии сущности
5. Все HTTP-методы имеют встроенную Swagger-документацию, которую можно переопределить
6. Для уточнения сущности в Swagger используйте `@Tag` на контроллере
7. Методы `PostOne` и `PutOne` автоматически валидируют входящие DTO с помощью `@Valid`