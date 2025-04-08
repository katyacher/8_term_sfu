import psycopg2
from faker import Faker
import random
from datetime import datetime, timedelta
from tqdm import tqdm
import csv
import os
from decimal import Decimal

# Конфигурация PostgreSQL
DB_CONFIG = {
    "host": "localhost",
    "database": "jewelry_db",
    "user": "postgres",
    "password": "postgres",
    "port": "5432"
}

fake = Faker(['ru_RU', 'en_US'])
BATCH_SIZE = 10000  # Увеличенный размер пакета для COPY

def create_connection():
    """Создание соединения с PostgreSQL"""
    return psycopg2.connect(**DB_CONFIG)

def generate_temp_csv(data, columns, filename):
    """Генерация временного CSV файла для COPY"""
    temp_dir = 'temp_csv'
    os.makedirs(temp_dir, exist_ok=True)
    path = os.path.join(temp_dir, filename)
    
    with open(path, 'w', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(columns)
        writer.writerows(data)
    
    return path

def populate_reference_tables(conn):
    """Заполнение справочников с проверкой существующих данных"""
    cur = conn.cursor()
    
    try:
        # 1. Страны (добавляем только отсутствующие)
        countries = [
            ('Россия', 'RU'), ('Италия', 'IT'), ('Франция', 'FR'),
            ('США', 'US'), ('Китай', 'CN'), ('Германия', 'DE'),
            ('Великобритания', 'GB'), ('Япония', 'JP'), ('Швейцария', 'CH')
        ]
        
        for country in countries:
            name, code = country
            # Проверяем существование страны
            cur.execute(
                "SELECT 1 FROM Country WHERE name = %s OR code = %s LIMIT 1",
                (name, code)
            )
            if not cur.fetchone():
                cur.execute(
                    "INSERT INTO Country (name, code) VALUES (%s, %s)",
                    (name, code)
                )
        
        # 2. Типы изделий (добавляем только отсутствующие)
        jewelry_types = [
            'Кольцо', 'Серьги', 'Подвеска', 'Браслет', 'Цепочка',
            'Кулон', 'Пирсинг', 'Запонки', 'Брошь', 'Диадема'
        ]
        
        for j_type in jewelry_types:
            cur.execute(
                "SELECT 1 FROM JewelryType WHERE name = %s LIMIT 1",
                (j_type,)
            )
            if not cur.fetchone():
                cur.execute(
                    "INSERT INTO JewelryType (name) VALUES (%s)",
                    (j_type,)
                )
        
        # 3. Материалы (добавляем только отсутствующие)
        materials = [
            ('metal', 'Золото 585', None, 'Высшая проба'),
            ('metal', 'Серебро 925', None, 'Стандарт'),
            ('metal', 'Платина', None, '950 проба'),
            ('metal', 'Палладий', None, '999 проба'),
            ('gemstone', 'Бриллиант', 1.0, 'VVS1'),
            ('gemstone', 'Сапфир', 0.8, 'AAA'),
            ('gemstone', 'Рубин', 0.6, 'Кровавый'),
            ('gemstone', 'Изумруд', 0.5, 'Чистый'),
            ('gemstone', 'Аметист', 0.3, 'Фиолетовый')
        ]
        
        for material in materials:
            m_type, name, carat, quality = material
            cur.execute(
                """SELECT 1 FROM Material 
                WHERE type = %s AND name = %s LIMIT 1""",
                (m_type, name)
            )
            if not cur.fetchone():
                cur.execute(
                    """INSERT INTO Material (type, name, carat, quality) 
                    VALUES (%s, %s, %s, %s)""",
                    (m_type, name, carat, quality)
                )
        
        conn.commit()
        
    except Exception as e:
        conn.rollback()
        raise e

def populate_customers(conn, num_records=100000):
    """Генерация клиентов через COPY"""
    cur = conn.cursor()
    
    # Генерация данных
    data = []
    for _ in tqdm(range(num_records), desc="Генерация клиентов"):
        # Генерируем более короткие номера телефонов
        phone = fake.numerify(text='+###########')  # Пример: +79211234567
        email = fake.unique.email()
        
        data.append((
            fake.name(),
            phone,
            email
        ))
    
    # Создаем временный CSV
    csv_path = generate_temp_csv(data, 
                               ['name', 'phone', 'email'],
                               'customers.csv')
    
    # Выполняем COPY
    with open(csv_path, 'r', encoding='utf-8') as f:
        cur.copy_expert(
            "COPY Customer (name, phone, email) FROM STDIN WITH CSV HEADER",
            f
        )
    conn.commit()
    
    # Удаляем временный файл
    os.remove(csv_path)

def populate_jewelry(conn, num_records=500000):
    """Генерация ювелирных изделий через COPY"""
    cur = conn.cursor()
    
    # Получаем справочные данные
    cur.execute("SELECT id FROM JewelryType")
    type_ids = [row[0] for row in cur.fetchall()]
    
    cur.execute("SELECT id FROM Country")
    country_ids = [row[0] for row in cur.fetchall()]
    
    manufacturers = ["Tiffany", "Cartier", "Swarovski", "Pandora", "Local", 
                    "Bvlgari", "Chopard", "Graff", "Harry Winston", "Van Cleef & Arpels"]
    
    # Генерация данных
    data = []
    for _ in tqdm(range(num_records), desc="Генерация изделий"):
        data.append((
            f"{random.choice(['Золотое', 'Серебряное', 'Платиновое'])} "
            f"{random.choice(['кольцо', 'серьги', 'подвеска', 'браслет', 'цепочка'])} "
            f"{fake.word(ext_word_list=['с бриллиантами', 'с сапфиром', 'с изумрудом', 'классическое'])}",
            round(random.uniform(100, 10000), 2),
            round(random.uniform(1.0, 100.0), 2),
            random.choice(type_ids),
            random.choice(manufacturers),
            random.choice(country_ids)
        ))
    
    # Создаем временный CSV
    csv_path = generate_temp_csv(data, 
                               ['name', 'price', 'weight', 'type_id', 'manufacturer', 'country_id'],
                               'jewelry.csv')
    
    # Выполняем COPY
    with open(csv_path, 'r', encoding='utf-8') as f:
        cur.copy_expert(
            "COPY Jewelry (name, price, weight, type_id, manufacturer, country_id) FROM STDIN WITH CSV HEADER",
            f
        )
    conn.commit()
    
    # Удаляем временный файл
    os.remove(csv_path)

def populate_orders(conn, num_records=500000):
    """Генерация заказов через COPY"""
    cur = conn.cursor()
    
    # Получаем справочные данные
    cur.execute("SELECT id FROM Customer")
    customer_ids = [row[0] for row in cur.fetchall()]
    
    # Генерация данных
    data = []
    start_date = datetime(2020, 1, 1)
    for _ in tqdm(range(num_records), desc="Генерация заказов"):
        data.append((
            start_date + timedelta(days=random.randint(0, 1460)),
            round(random.uniform(100, 50000), 2),
            random.choice(customer_ids)
        ))
    
    # Создаем временный CSV
    csv_path = generate_temp_csv(data, 
                               ['order_date', 'total', 'customer_id'],
                               'orders.csv')
    
    # Выполняем COPY
    with open(csv_path, 'r', encoding='utf-8') as f:
        cur.copy_expert(
            "COPY Orders (order_date, total, customer_id) FROM STDIN WITH CSV HEADER",
            f
        )
    conn.commit()
    
    # Удаляем временный файл
    os.remove(csv_path)

def populate_jewelry_materials(conn):
    """Связь изделий с материалами"""
    cur = conn.cursor()
    
    # Получаем данные
    cur.execute("SELECT id FROM Jewelry")
    jewelry_ids = [row[0] for row in cur.fetchall()]
    
    cur.execute("SELECT id FROM Material WHERE type = 'metal'")
    metal_ids = [row[0] for row in cur.fetchall()]
    
    cur.execute("SELECT id FROM Material WHERE type = 'gemstone'")
    gem_ids = [row[0] for row in cur.fetchall()]
    
    # Генерация данных
    data = []
    for jewelry_id in tqdm(jewelry_ids, desc="Связи материалов"):
        metals = random.sample(metal_ids, random.randint(1, 2))
        gems = random.sample(gem_ids, random.randint(0, 3))
        
        for material_id in metals + gems:
            data.append((jewelry_id, material_id))
            
            if len(data) % BATCH_SIZE == 0:
                cur.executemany(
                    "INSERT INTO JewelryMaterial (jewelry_id, material_id) VALUES (%s, %s)",
                    data
                )
                conn.commit()
                data = []
    
    if data:
        cur.executemany(
            "INSERT INTO JewelryMaterial (jewelry_id, material_id) VALUES (%s, %s)",
            data
        )
        conn.commit()

def populate_order_items(conn):
    """Позиции заказов"""
    cur = conn.cursor()
    
    # Получаем данные
    cur.execute("SELECT id FROM Orders")
    order_ids = [row[0] for row in cur.fetchall()]
    
    cur.execute("SELECT id, price FROM Jewelry")
    jewelry_data = [(row[0], Decimal(str(row[1]))) for row in cur.fetchall()]  # Явное преобразование в Decimal
    
    # Генерация данных
    data = []
    for order_id in tqdm(order_ids, desc="Позиции заказов"):
        items = random.sample(jewelry_data, random.randint(1, 5))
        for jewelry_id, price in items:
            # Умножаем Decimal на Decimal
            item_price = price * Decimal(str(random.uniform(0.9, 1.1))).quantize(Decimal('0.01'))
            data.append((
                order_id,
                jewelry_id,
                random.randint(1, 3),
                item_price
            ))
            
            if len(data) % BATCH_SIZE == 0:
                cur.executemany(
                    """INSERT INTO OrderItem (order_id, jewelry_id, quantity, price) 
                    VALUES (%s, %s, %s, %s)""",
                    data
                )
                conn.commit()
                data = []
    
    if data:
        cur.executemany(
            """INSERT INTO OrderItem (order_id, jewelry_id, quantity, price) 
            VALUES (%s, %s, %s, %s)""",
            data
        )
        conn.commit()

def update_order_totals(conn):
    """Обновление итоговых сумм заказов"""
    cur = conn.cursor()
    cur.execute("""
        UPDATE Orders o
        SET total = (
            SELECT SUM(quantity * price) 
            FROM OrderItem 
            WHERE order_id = o.id
        )
    """)
    conn.commit()

if __name__ == "__main__":
    # Настройка подключения к БД
    print("Настройка подключения к PostgreSQL...")
    print(f"Хост: {DB_CONFIG['host']}")
    print(f"База данных: {DB_CONFIG['database']}")
    print(f"Пользователь: {DB_CONFIG['user']}")
    
    conn = create_connection()
    
    try:
        print("\n1. Заполнение справочников...")
        populate_reference_tables(conn)
        
        print("\n2. Заполнение клиентов...")
        populate_customers(conn)
       
        print("\n3. Заполнение ювелирных изделий...")
        populate_jewelry(conn)
      
        print("\n4. Заполнение заказов...")
        populate_orders(conn)
      
        print("\n5. Связи материалов...")
        populate_jewelry_materials(conn)
        
        print("\n6. Позиции заказов...")
        populate_order_items(conn)
        
        print("\n7. Обновление сумм заказов...")
        update_order_totals(conn)
        
        print("\nГотово! Все данные успешно загружены.")
        
    except Exception as e:
        conn.rollback()
        print(f"\nОшибка: {e}")
    finally:
        conn.close()
