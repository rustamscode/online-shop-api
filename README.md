# API для онлайн магазина
**Автор**: Агагюль Рустамов

# Эндпоинты
## Эндпоинты для взаимодействия с товарами
### 1. Получение всех продуктов 
**URL**: `/products`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает список всех продуктов.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Массив объектов Product<br>
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
**URL**: `/products/filter`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает отфильтрованный и отсортированный список продуктов.<br>
**Параметры запроса** :
 - name (опционально): Строка для поиска по имени.
 - minPrice (опционально): Минимальная цена.
 - maxPrice (опционально): Максимальная цена.
 - available (по умолчанию true): Фильтр по доступности.
 - sortBy (по умолчанию name): Поле для сортировки (name, price и т.д.).
 - sortDirection (по умолчанию asc): Направление сортировки (asc, desc).
 - limit (по умолчанию 5): Ограничение на количество возвращаемых записей.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Массив объектов Product<br>
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
**URL**: `/products/{id}`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает продукт по его идентификатору.<br>
**Параметры пути** :
 - id: Идентификатор продукта.
**Ответ** :
 - Код: 200 OK
 - Тело: Объект Product<br>
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
**URL**: `/products/create`<br>
**Метод** : `POST`<br>
**Описание** : Создаёт новый продукт.<br>
**Тело запроса**<br>
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
 - Тело: Объект Product<br>
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
**URL**: `/products/update/{id}`<br>
**Метод** : `PUT`<br>
**Описание** : Обновляет данные продукта по его идентификатору.<br>
**Тело запроса**<br>
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
 - Тело: Объект Product<br>
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
**URL**: `/products/delete/{id}`<br>
**Метод** : `DELETE`<br>
**Описание** : Удаляет продукт по его идентификатору.<br>
**Параметры пути** :
 - id: Идентификатор продукта.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Отсутствует




