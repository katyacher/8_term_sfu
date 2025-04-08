import csv
from faker import Faker
import random
from datetime import datetime, timedelta

fake = Faker('ru_RU')

# 1. Генерируем JewelryType (10 типов)
jewelry_types = [
    {"id": 1, "name": "Кольцо"},
    {"id": 2, "name": "Серьги"},
    {"id": 3, "name": "Цепочка"},
    {"id": 4, "name": "Браслет"},
    {"id": 5, "name": "Подвеска"},
    {"id": 6, "name": "Кулон"},
    {"id": 7, "name": "Пирсинг"},
    {"id": 8, "name": "Запонки"},
    {"id": 9, "name": "Брошь"},
    {"id": 10, "name": "Диадема"}
]

# 2. Генерируем Material (50 материалов)
materials = []
for i in range(1, 51):
    if i % 2 == 0:
        materials.append({
            "id": i,
            "type": "gemstone",
            "name": fake.word(ext_word_list=["Бриллиант", "Рубин", "Сапфир", "Изумруд", "Аметист"]),
            "carat": round(random.uniform(0.1, 5.0), 2),
            "quality": fake.word(ext_word_list=["VVS1", "VS1", "SI1", "Идеальная огранка"])
        })
    else:
        materials.append({
            "id": i,
            "type": "metal",
            "name": fake.word(ext_word_list=["Золото 585", "Серебро 925", "Платина", "Титан", "Палладий"]),
            "carat": None,
            "quality": fake.word(ext_word_list=["Высшая проба", "Стандарт", "Медицинский"])
        })

# 3. Генерируем Jewelry (2000 изделий)
jewelries = []
manufacturers = ["Tiffany", "Cartier", "Swarovski", "Pandora", "Local Jewelry"]
for i in range(1, 2001):
    jewelries.append({
        "id": i,
        "name": f"{fake.word(ext_word_list=['Обручальное', 'Свадебное', 'Золотое', 'Серебряное'])} {random.choice(jewelry_types)['name']}",
        "price": round(random.uniform(100, 10000), 2),
        "weight": round(random.uniform(1.0, 100.0), 2),
        "type_id": random.randint(1, 10),
        "manufacturer": random.choice(manufacturers)
    })

# 4. Генерируем Customer (1000 клиентов)
customers = []
for i in range(1, 1001):
    customers.append({
        "id": i,
        "name": fake.name(),
        "phone": fake.phone_number(),
        "email": fake.email()
    })

# 5. Генерируем Orders (5000 заказов)
orders = []
start_date = datetime(2022, 1, 1)
for i in range(1, 5001):
    orders.append({
        "id": i,
        "order_date": (start_date + timedelta(days=random.randint(0, 730))).strftime('%Y-%m-%d'),
        "total": 0,  # Будет рассчитано позже
        "customer_id": random.randint(1, 1000)
    })

# 6. Генерируем OrderItem (10000 позиций)
order_items = []
for i in range(1, 10001):
    jewelry = random.choice(jewelries)
    order_items.append({
        "order_id": random.randint(1, 5000),
        "jewelry_id": jewelry['id'],
        "quantity": random.randint(1, 5),
        "price": jewelry['price']
    })

# 7. JewelryMaterial (10000 связей)
jewelry_materials = []
for jewelry in jewelries:
    for _ in range(random.randint(1, 5)):
        jewelry_materials.append({
            "jewelry_id": jewelry['id'],
            "material_id": random.randint(1, 50)
        })

# Сохранение в CSV
def save_to_csv(filename, data, columns):
    with open(filename, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=columns)
        writer.writeheader()
        writer.writerows(data)

save_to_csv('jewelry_types.csv', jewelry_types, ['id', 'name'])
save_to_csv('materials.csv', materials, ['id', 'type', 'name', 'carat', 'quality'])
save_to_csv('jewelries.csv', jewelries, ['id', 'name', 'price', 'weight', 'type_id', 'manufacturer'])
save_to_csv('customers.csv', customers, ['id', 'name', 'phone', 'email'])
save_to_csv('orders.csv', orders, ['id', 'order_date', 'total', 'customer_id'])
save_to_csv('order_items.csv', order_items, ['order_id', 'jewelry_id', 'quantity', 'price'])
save_to_csv('jewelry_materials.csv', jewelry_materials, ['jewelry_id', 'material_id'])
