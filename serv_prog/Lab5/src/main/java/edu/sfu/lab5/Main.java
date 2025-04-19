package edu.sfu.lab5;

import edu.sfu.lab5.services.*;
import edu.sfu.lab5.dao.*;
import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.*;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Тестовые сервисы
            TestSrvs testService = new TestSrvs();
            System.out.println("Country name: " + testService.getName(1));
            System.out.println("Names in range: " + testService.getNamesInRange(1, 3));

            // 2. Работа с DAO
            CountryDAO countryDAO = new CountryDAO();
            JewelryTypeDAO jewelryTypeDAO = new JewelryTypeDAO();
            
            // Получение всех стран
            DAO.begin();
            List<Country> countries = countryDAO.getAll();
            DAO.commit();
            System.out.println("Countries:");
            countries.forEach(c -> System.out.println(c.getName()));
            
            // Получение всех типов украшений
            DAO.begin();
            List<JewelryType> types = jewelryTypeDAO.getAll();
            DAO.commit();
            System.out.println("Jewelry Types:");
            types.forEach(t -> System.out.println(t.getName()));
            
            // 3. Сервис с фильтрами
            JewelryService jewelryService = new JewelryService();
            
            // Пример 1: Фильтр по цене и типу (первые 20)
            DAO.begin();
            List<Jewelry> results1 = jewelryService.searchJewelry(
                null,
                new BigDecimal("100.00"),
                new BigDecimal("500.00"),
                2,
                null
            );
            DAO.commit();
            System.out.println("Пример 1: Украшения типа кольцо с ценой 100-500");
            jewelryService.printJewelryList(results1);
            
            // Пример 2: Фильтр по названию и производителю
            DAO.begin();
            List<Jewelry> results2 = jewelryService.searchJewelry(
                "кольцо",
                null,
                null,
                null,
                "Tiffany"
            );
            DAO.commit();
            System.out.println("\nПример 2: Украшения типа кольцо от Tiffany");
            jewelryService.printJewelryList(results2);
            
            // 5. Дорогие украшения с камнями
            JewelryDAO jewelryDAO = new JewelryDAO();
            DAO.begin();
            List<Object[]> expensiveJewelry_page1 = jewelryDAO.findExpensiveGemstoneJewelry(0, 20);
            DAO.commit();
            
            System.out.println("\nДорогие украшения с камнями:");
            for (Object[] row : expensiveJewelry_page1) {
                System.out.printf("ID: %d, Название: %s, Цена: %.2f, Материал: %s%n",
                    row[0], row[1], row[2], row[4]);
            }
            
            // 7. Демонстрация один-ко-многим
            DemoService demoService = new DemoService();
            DAO.begin();
            demoService.demonstrateOneToManyWithDbPagination(10);// 10 элементов на страницу
            DAO.commit();
            
        } catch (Exception e) {
            DAO.rollback();
            e.printStackTrace();
        } finally {
            // Закрываем SessionFactory  в самом конце
            DAO.shutdown();
        }
    }
}