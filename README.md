# API для онлайн магазина
**Автор**: Агагюль Рустамов

# Эндпоинты
## Эндпоинты для взаимодействия с товарами
### 1. Получение всех продуктов 
**URL**: `/products`
**Метод** : `GET`
**Описание** : Возвращает список всех продуктов.
**Ответ** :
 - Код: 200 OK
 - Тело: Массив объектов Product
**Пример тела ответа**
```json
[
  {
    "id": 1,
    "name": "Product 1",
    "info": "Product 1 info",
    "price": 150,
    "stock": 0,
    "available": false
  },
  {
    "id": 2,
    "name": "Product 2",
    "info": "Product 2 info",
    "price": 200,
    "stock": 0,
    "available": false
  }
]
```

### 2. Фильтрация и сортировка продуктов
**URL**: `/products/filter`
**Метод** : `GET`
**Описание** : Возвращает отфильтрованный и отсортированный список продуктов.
**Параметры запроса** :
 - name (опционально): Строка для поиска по имени.
 - minPrice (опционально): Минимальная цена.
 - maxPrice (опционально): Максимальная цена.
 - available (по умолчанию true): Фильтр по доступности.
 - sortBy (по умолчанию name): Поле для сортировки (name, price и т.д.).
 - sortDirection (по умолчанию asc): Направление сортировки (asc, desc).
 - limit (по умолчанию 5): Ограничение на количество возвращаемых записей.
**Ответ** :
 - Код: 200 OK
 - Тело: Массив объектов Product
**Пример тела ответа**
```json
[
  {
    "id": 2,
    "name": "Product 2",
    "info": "Product 2 info",
    "price": 200,
    "stock": 0,
    "available": false
  },
  {
    "id": 1,
    "name": "Product 1",
    "info": "Product 1 info",
    "price": 150,
    "stock": 0,
    "available": false
  }
]
```

### 3. Получение продукта по ID
**URL**: `/products/{id}`
**Метод** : `GET`
**Описание** : Возвращает продукт по его идентификатору.
**Параметры пути** :
 - id: Идентификатор продукта.
**Ответ** :
 - Код: 200 OK
 - Тело: Объект Product
**Пример тела ответа**
```json
  {
    "id": 2,
    "name": "Product 2",
    "info": "Product 2 info",
    "price": 200,
    "stock": 0,
    "available": false
  }
```

### 4. Создание продукта
**URL**: `/products/create`
**Метод** : `POST`
**Описание** : Создаёт новый продукт.
**Тело запроса**
Объект ProductRequest
```json
  {
  "name": "New Product",
  "info": "New product description",
  "price": 100
}
```
**Ответ** :
 - Код: 201 OK
 - Тело: Объект Product
**Пример тела ответа**
```json
  {
    "id": 3,
    "name": "New Product",
    "info": "New product description",
    "price": 100
    "stock": 0,
    "available": false
  }
```

### 5. Обновление продукта
**URL**: `/products/update/{id}`
**Метод** : `PUT`
**Описание** : Обновляет данные продукта по его идентификатору.
**Тело запроса**
Объект ProductRequest
```json
  {
  "name": "Updated Product",
  "info": "Updated Product description",
  "price": 100
}
```
**Ответ** :
 - Код: 201 OK
 - Тело: Объект Product
**Пример тела ответа**
```json
  {
    "id": 3,
    "name": "Updated Product",
    "info": "Updated Product description",
    "price": 100
    "stock": 0,
    "available": false
  }
```

### 6. Удаление продукта
**URL**: `/products/delete/{id}`
**Метод** : `DELETE`
**Описание** : Удаляет продукт по его идентификатору.
**Параметры пути** :
 - id: Идентификатор продукта.
**Ответ** :
 - Код: 200 OK
 - Тело: Отсутствует




