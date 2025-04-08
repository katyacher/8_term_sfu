-- 1. JewelryType (Типы изделий)
INSERT INTO JewelryType (name) VALUES
('Кольцо'), ('Серьги'), ('Цепочка'), ('Браслет'), ('Подвеска'),
('Кулон'), ('Пирсинг'), ('Запонки'), ('Брошь'), ('Диадема');

-- 2. Material (Материалы и камни)
INSERT INTO Material (type, name, carat, quality) VALUES
('metal', 'Золото 585', NULL, 'Высшая проба'),
('metal', 'Серебро 925', NULL, 'Стандарт'),
('gemstone', 'Бриллиант', 1.2, 'VVS1'),
('metal', 'Платина', NULL, '950 проба'),
('gemstone', 'Сапфир', 0.8, 'Синий'),
('gemstone', 'Изумруд', 0.5, 'AAA'),
('metal', 'Титан', NULL, 'Медицинский'),
('gemstone', 'Рубин', 0.7, 'Кровавый'),
('metal', 'Палладий', NULL, '999 проба'),
('gemstone', 'Аметист', 0.3, 'Фиолетовый');

-- 3. Jewelry (Ювелирные изделия)
INSERT INTO Jewelry (name, price, weight, type_id, manufacturer) VALUES
('Кольцо обручальное', 1500.00, 5.0, 1, 'Tiffany'),
('Серьги-гвоздики', 450.00, 3.2, 2, 'Sokolov'),
('Цепочка золотая', 1200.00, 8.5, 3, 'Adamas'),
('Браслет Pandora', 950.00, 12.0, 4, 'Pandora'),
('Подвеска-сердце', 300.00, 2.1, 5, 'Swarovski'),
('Кулон с бриллиантом', 2500.00, 4.8, 6, 'Cartier'),
('Пирсинг в нос', 150.00, 1.5, 7, 'Local Jewelry'),
('Запонки серебряные', 200.00, 6.0, 8, 'Frey Wille'),
('Брошь Vintage', 1800.00, 7.3, 9, 'Chopard'),
('Диадема свадебная', 5000.00, 15.0, 10, 'Harry Winston');

-- 4. JewelryMaterial (Связь изделий и материалов)
INSERT INTO JewelryMaterial (jewelry_id, material_id) VALUES
(1,1), (1,3), (2,2), (3,1), (4,4), (5,5), (6,3), (7,7), (8,2), (9,9);

-- 5. Customer (Клиенты)
INSERT INTO Customer (name, phone, email) VALUES
('Иванова Анна', '+79161234567', 'anna@mail.ru'),
('Петров Дмитрий', '+79035556677', 'dmitry@yandex.ru'),
('Сидорова Мария', '+79269874563', 'maria@gmail.com'),
('Козлов Артем', '+79031237894', 'artem@mail.com'),
('Новикова Елена', '+79150983456', 'elena@yahoo.com'),
('Васильев Игорь', '+79087651234', 'igor@bk.ru'),
('Морозова Ольга', '+79265439876', 'olga@inbox.ru'),
('Лебедев Павел', '+79024561239', 'pavel@list.ru'),
('Смирнова Виктория', '+79167894523', 'vika@rambler.ru'),
('Кузнецов Андрей', '+79035671234', 'andrey@gmail.com');

-- 6. Orders (Заказы)
INSERT INTO Orders (order_date, total, customer_id) VALUES
('2023-10-01', 1500.00, 1),
('2023-10-02', 450.00, 2),
('2023-10-03', 1200.00, 3),
('2023-10-04', 950.00, 4),
('2023-10-05', 300.00, 5),
('2023-10-06', 2500.00, 6),
('2023-10-07', 150.00, 7),
('2023-10-08', 200.00, 8),
('2023-10-09', 1800.00, 9),
('2023-10-10', 5000.00, 10);

-- 7. OrderItem (Позиции заказов)
INSERT INTO OrderItem (order_id, jewelry_id, quantity, price) VALUES
(1,1,1,1500.00), (2,2,1,450.00), (3,3,1,1200.00), (4,4,1,950.00),
(5,5,1,300.00), (6,6,1,2500.00), (7,7,1,150.00), (8,8,1,200.00),
(9,9,1,1800.00), (10,10,1,5000.00);

