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
 - id: Идентификатор продукта.<br>
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

## Эндпоинты для взаимодействия с продажами
### 1. Получение всех продаж 
**URL**: `/sales`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает список всех продаж.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Массив объектов Sale<br>
**Пример тела ответа**
```json
[
  {
    "id": 1,
    "name": "Sale 1",
    "product": {
      "id": 1,
      "name": "Product 1",
      "info": "Product 1 info",
      "price": 150,
      "stock": 4,
      "available": true
    },
    "amount": 2,
    "price": 300
  },
  {
    "id": 2,
    "name": "Sale 2",
    "product": {
      "id": 1,
      "name": "Product 1",
      "info": "Product 1 info",
      "price": 150,
      "stock": 4,
      "available": true
    },
    "amount": 4,
    "price": 600
  }
]
```

### 2. Получение информации о продаже по ID
**URL**: `/sales/{id}`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает информацию о продаже с указанным id.<br>
**Параметры пути** :
 - id: Идентификатор продажи.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Объект Sale<br>
**Пример тела ответа**
```json
   {
    "id": 2,
    "name": "Sale 2",
    "product": {
      "id": 1,
      "name": "Product 1",
      "info": "Product 1 info",
      "price": 150,
      "stock": 4,
      "available": true
    },
    "amount": 4,
    "price": 600
  }
```

### 3. Создание новой продажи
**URL**: `/sales/create`<br>
**Метод** : `POST`<br>
**Описание** : Создаёт новую продажу.<br>
**Тело запроса**<br>
Объект SaleRequest
```json
  {
  "name": "Sale 2",
  "productId": 1,
  "amount": 4
}
```
**Ответ** :
 - Код: 201 OK
 - Тело: Объект Sale<br>
**Пример тела ответа**
```json
  {
  "id": 2,
  "name": "Sale 2",
  "product": {
    "id": 1,
    "name": "Product 1",
    "info": "Product 1 info",
    "price": 200,
    "stock": 6,
    "available": true
  },
  "amount": 4,
  "price": 800
}
```

### 4. Обновление продажи
**URL**: `/sales/update/{id}`<br>
**Метод** : `PUT`<br>
**Описание** : Обновляет информацию о продаже с указанным id.<br>
**Тело запроса**<br>
Объект SaleRequest
```json
  {
  "name": "Updated Sale",
  "productId": 1,
  "amount": 1
}
```
**Ответ** :
 - Код: 201 OK
 - Тело: Объект Sale<br>
**Пример тела ответа**
```json
 {
  "id": 1,
  "name": "Updated Sale",
  "product": {
    "id": 1,
    "name": "Product 1",
    "info": "Product 1 info",
    "price": 200,
    "stock": 6,
    "available": true
  },
  "amount": 1,
  "price": 200
}
```

### 5. Удаление продажи
**URL**: `/sales/delete/{id}`<br>
**Метод** : `DELETE`<br>
**Описание** : Удаляет продажу с указанным id.<br>
**Параметры пути** :
 - id: Идентификатор продажи.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Отсутствует

## Эндпоинты для взаимодействия с поставками
### 1. Получение всех поставок 
**URL**: `/supplies`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает список всех поставок.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Массив объектов Supply<br>
**Пример тела ответа**
```json
[
  {
    "id": 1,
    "name": "Supply 1",
    "product": {
      "id": 1,
      "name": "Product 1",
      "info": "Product 1 info",
      "price": 150,
      "stock": 4,
      "available": true
    },
    "amount": 10
  },
  {
    "id": 2,
    "name": "Supply 2",
    "product": {
      "id": 2,
      "name": "Product 2",
      "info": "Product 2 info",
      "price": 200,
      "stock": 6,
      "available": true
    },
    "amount": 10
  }
]
```

### 2. Получение информации о поставке по ID
**URL**: `/supplies/{id}`<br>
**Метод** : `GET`<br>
**Описание** : Возвращает информацию о поставке с указанным id.<br>
**Параметры пути** :
 - id: Идентификатор поставки.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Объект Supply<br>
**Пример тела ответа**
```json
   {
    "id": 2,
    "name": "Supply 2",
    "product": {
      "id": 2,
      "name": "Product 2",
      "info": "Product 2 info",
      "price": 200,
      "stock": 6,
      "available": true
    },
    "amount": 10
  }
```

### 3. Создание новой поставки
**URL**: `/supplies/create`<br>
**Метод** : `POST`<br>
**Описание** : Создаёт новую поставку.<br>
**Тело запроса**<br>
Объект SupplyRequest
```json
  {
  "name": "Supply 2",
  "productId": 1,
  "amount": 2
}
```
**Ответ** :
 - Код: 201 OK
 - Тело: Объект Supply<br>
**Пример тела ответа**
```json
   {
    "id": 2,
    "name": "Supply 2",
    "product": {
      "id": 1,
      "name": "Product 1",
      "info": "Product 1 info",
      "price": 200,
      "stock": 6,
      "available": true
    },
    "amount": 2
  }
```

### 4. Обновление поставки
**URL**: `/supplies/update/{id}`<br>
**Метод** : `PUT`<br>
**Описание** : Обновляет информацию о поставке с указанным id.<br>
**Тело запроса**<br>
Объект SupplyRequest
```json
  {
  "name": "Updated Supply",
  "productId": 1,
  "amount": 1
}
```
**Ответ** :
 - Код: 201 OK
 - Тело: Объект Supply<br>
**Пример тела ответа**
```json
   {
    "id": 2,
    "name": "Updated Supply",
    "product": {
      "id": 1,
      "name": "Product 1",
      "info": "Product 1 info",
      "price": 200,
      "stock": 6,
      "available": true
    },
    "amount": 1
  }
```

### 5. Удаление поставки
**URL**: `/supplies/delete/{id}`<br>
**Метод** : `DELETE`<br>
**Описание** : Удаляет поставку с указанным id.<br>
**Параметры пути** :
 - id: Идентификатор поставки.<br>
**Ответ** :
 - Код: 200 OK
 - Тело: Отсутствует











