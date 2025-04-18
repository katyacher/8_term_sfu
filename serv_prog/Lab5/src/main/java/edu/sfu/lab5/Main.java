package edu.sfu.lab5;

import edu.sfu.lab5.services.JewelryService;
import edu.sfu.lab5.services.TestSrvs;
import edu.sfu.lab5.dao.*;
import edu.sfu.lab5.model.Country;
import edu.sfu.lab5.model.Jewelry;
import edu.sfu.lab5.model.JewelryType;
//import edu.sfu.lab5.model.Material;

import java.math.BigDecimal;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
import edu.sfu.lab5.services.DemoService;

public class Main {
    public static void main(String[] args) {
        // Тест получения имени
        String countryName = TestSrvs.getName(1);
        System.out.println("Country name: " + countryName);

        // Тест получения диапазона
        System.out.println("Names in range: " + TestSrvs.getNamesInRange(1, 3));

        // Тест создания записи
        /*
        int created = TestSrvs.createCountry("Канада", "CA");
        System.out.println("Created rows: " + created);
        */
        
        // Инициализация DAO
        CountryDAO countryDAO = new CountryDAO();
        JewelryTypeDAO jewelryTypeDAO = new JewelryTypeDAO();
        
        // Получение всех стран
        List<Country> countries = countryDAO.getAll();
        System.out.println("Countries:");
        countries.forEach(c -> System.out.println(c.getName()));
        
        // Получение всех типов украшений
        List<JewelryType> jewelryTypes = jewelryTypeDAO.getAll();
        System.out.println("\nJewelry Types:");
        jewelryTypes.forEach(t -> System.out.println(t.getName()));
        
        
        
        //4. Получение информации об украшениях с фильтрами по входным параметрам
        JewelryService service = new JewelryService();
        
        //  Поиск по диапазону цен и типу
        System.out.println("Пример 1: Украшения типа кольцо с ценой 100-500");
        List<Jewelry> results1 = service.searchJewelry(
            null,
            new BigDecimal("100.00"),
            new BigDecimal("500.00"),
            2,
            null
        );
        service.printJewelryList(results1);

        //  Поиск по названию и производителю
        System.out.println("\nПример 2: Украшения типа кольцо от Tiffany");
        List<Jewelry> results2 = service.searchJewelry(
            "кольцо",
            null,
            null,
            null,
            "Tiffany"
        );
        service.printJewelryList(results2);
        
        
     // 5. Получение списка дорогих украшений с камнем
        JewelryDAO jewelryDAO = new JewelryDAO();
        List<Object[]> results = jewelryDAO.findExpensiveGemstoneJewelry();

        for (Object[] row : results) {
            System.out.println("Jewelry ID: " + row[0]);
            System.out.println("Name: " + row[1]);
            System.out.println("Price: " + row[2]);
            System.out.println("Weight: " + row[3]);
            System.out.println("Material: " + row[4] + " (" + row[5] + ")");
            System.out.println("Carat: " + row[6]);
            System.out.println("-------------------");
        }
 /*    
        // 6. CRUD операции
        // Создание нового украшения --- пример 
        Jewelry newJewelry = new Jewelry();
        newJewelry.setName("Золотое кольцо");
        newJewelry.setPrice(new BigDecimal("999.99"));
        newJewelry.setWeight(new BigDecimal("5.75"));
        newJewelry.setManufacturer("Русские золотые изделия");
        
        Integer newId = jewelryDAO.save(newJewelry);
        System.out.println("Создано украшение с ID: " + newId);
        
        // Получение украшения по ID --- пример 
        Jewelry jewel = jewelryDAO.getById(newId);
        System.out.println("Получено украшение: " + jewel.getName());
        
        // Обновление украшения --- пример 
        jewel.setPrice(new BigDecimal("1099.99"));
        jewelryDAO.update(jewel);
        
        // Удаление украшения --- пример 
        jewelryDAO.delete(newId);
        
        // Получение всех украшений --- пример 
        List<Jewelry> allJewelry = jewelryDAO.getAll();
        System.out.println("Всего украшений: " + allJewelry.size());
        
    */    
        
        
        //7. Демонстрационный метод один ко многим
        
        DemoService demoService = new DemoService();
        demoService.demonstrateOneToMany();
        
        
     /*   
        
     // Создание нового украшения
        Jewelry newJewelry = new Jewelry();
        newJewelry.setName("Золотое кольцо");
        newJewelry.setPrice(new BigDecimal("999.99"));
        newJewelry.setWeight(new BigDecimal("5.75"));
        newJewelry.setManufacturer("Русские золотые изделия");

        // Получаем тип украшения из базы 
        //JewelryTypeDAO jewelryTypeDAO = new JewelryTypeDAO(); -- см. выше по коду
        JewelryType ringType = jewelryTypeDAO.getById(1); // ID=1 для типа "Кольцо"
        newJewelry.setType(ringType);

        // Получаем страну производителя из базы
       // CountryDAO countryDAO = new CountryDAO(); -- см. выше по коду
        Country russia = countryDAO.getById(1); // ID=1 для страны "Россия"
        newJewelry.setCountry(russia);

        // Получаем материалы для украшения
        MaterialDAO materialDAO = new MaterialDAO();
        Material gold = materialDAO.getById(1); // ID=1 для "Золото"
        Material diamond = materialDAO.getById(2); // ID=2 для "Бриллиант"

        // Создаем набор материалов и добавляем в украшение
        Set<Material> materials = new HashSet<>();
        materials.add(gold);
        materials.add(diamond);
        newJewelry.setMaterials(materials);

        // Сохраняем украшение в базе данных
        //JewelryDAO jewelryDAO = new JewelryDAO(); -- см. выше по коду
        Integer newJewelryId = jewelryDAO.save(newJewelry);

        System.out.println("Создано новое украшение с ID: " + newJewelryId);

        // Для проверки получаем созданное украшение из базы
        Jewelry savedJewelry = jewelryDAO.getById(newJewelryId);
        System.out.println("Информация о сохраненном украшении:");
        System.out.println("Название: " + savedJewelry.getName());
        System.out.println("Цена: " + savedJewelry.getPrice());
        System.out.println("Вес: " + savedJewelry.getWeight());
        System.out.println("Производитель: " + savedJewelry.getManufacturer());
        System.out.println("Тип: " + savedJewelry.getType().getName());
        System.out.println("Страна: " + savedJewelry.getCountry().getName());
        System.out.println("Материалы: " + 
            savedJewelry.getMaterials().stream()
                .map(Material::getName)
                .collect(Collectors.joining(", ")));
                */
    }
}