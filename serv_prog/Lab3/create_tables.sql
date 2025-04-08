DROP TABLE IF EXISTS OrderItem;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS JewelryMaterial;
DROP TABLE IF EXISTS Jewelry;
DROP TABLE IF EXISTS Material;
DROP TABLE IF EXISTS JewelryType;
DROP TABLE IF EXISTS Country;

-- Создание таблицы стран
CREATE TABLE Country (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    code VARCHAR(2) NOT NULL UNIQUE
);

-- Создание таблицы типов изделий
CREATE TABLE JewelryType (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Создание таблицы материалов
CREATE TABLE Material (
    id SERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL CHECK (type IN ('metal', 'gemstone')),
    name VARCHAR(100) NOT NULL,
    carat DECIMAL(5,2),
    quality VARCHAR(50)
);

-- Создание таблицы ювелирных изделий 
CREATE TABLE Jewelry (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) CHECK (price > 0),
    weight DECIMAL(8,2) CHECK (weight > 0),
    type_id INT NOT NULL REFERENCES JewelryType(id) ON DELETE RESTRICT,
    manufacturer VARCHAR(100) NOT NULL,
    country_id INT REFERENCES Country(id) ON DELETE SET NULL
);

-- Создание таблицы связи материалов и изделий 
CREATE TABLE JewelryMaterial (
    jewelry_id INT NOT NULL REFERENCES Jewelry(id) ON DELETE CASCADE,
    material_id INT NOT NULL REFERENCES Material(id) ON DELETE CASCADE,
    PRIMARY KEY (jewelry_id, material_id)
);

-- Создание таблицы клиентов
CREATE TABLE Customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(30) UNIQUE,
    email VARCHAR(100) UNIQUE 
);

-- Создание таблицы заказов
CREATE TABLE Orders (
    id SERIAL PRIMARY KEY,
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    total DECIMAL(10,2) CHECK (total >= 0),
    customer_id INT NOT NULL REFERENCES Customer(id) ON DELETE CASCADE
);

-- Создание таблицы позиций заказа
CREATE TABLE OrderItem (
    order_id INT NOT NULL REFERENCES Orders(id) ON DELETE CASCADE,
    jewelry_id INT NOT NULL REFERENCES Jewelry(id) ON DELETE RESTRICT,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    PRIMARY KEY (order_id, jewelry_id)
);
