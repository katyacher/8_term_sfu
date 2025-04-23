package edu.sfu.lab6.services;
/*
import edu.sfu.lab6.dao.*;
import edu.sfu.lab6.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class DemoService {

    private final CountryDAO countryDAO;
    private final JewelryDAO jewelryDAO;
    private final JewelryTypeDAO jewelryTypeDAO;
    private final MaterialDAO materialDAO;

    @Autowired
    public DemoService(CountryDAO countryDAO, 
                      JewelryDAO jewelryDAO,
                      JewelryTypeDAO jewelryTypeDAO,
                      MaterialDAO materialDAO) {
        this.countryDAO = countryDAO;
        this.jewelryDAO = jewelryDAO;
        this.jewelryTypeDAO = jewelryTypeDAO;
        this.materialDAO = materialDAO;
    }

    public void demonstrateOneToMany(int limit) {
        initializeReferenceData();
        List<Jewelry> jewelryList = jewelryDAO.findFirstNByCountryId(1, limit);
        printResults(jewelryList);
    }

    private void initializeReferenceData() {
        // Создаем страну, если не существует
        if (countryDAO.getById(1) == null) {
            Country russia = new Country();
            russia.setId(1);
            russia.setName("Россия");
            russia.setCode("RU");
            countryDAO.save(russia);
        }

        // Создаем тип украшения, если не существует
        if (jewelryTypeDAO.getById(1) == null) {
            JewelryType ringType = new JewelryType();
            ringType.setId(1);
            ringType.setName("Кольцо");
            jewelryTypeDAO.save(ringType);
        }

        // Создаем материалы, если не существуют
        if (materialDAO.getById(1) == null) {
            Material gold = new Material();
            gold.setId(1);
            gold.setName("Золото");
            gold.setType("metal");
            gold.setCarat(new BigDecimal("24.00"));
            materialDAO.save(gold);
        }

        if (materialDAO.getById(2) == null) {
            Material diamond = new Material();
            diamond.setId(2);
            diamond.setName("Бриллиант");
            diamond.setType("gemstone");
            diamond.setCarat(new BigDecimal("1.00"));
            diamond.setQuality("VS1");
            materialDAO.save(diamond);
        }
    }

    private void printResults(List<Jewelry> jewelryList) {
        System.out.println("\n=== Демонстрация связи Country -> Jewelry (OneToMany) ===");
        System.out.println("Вывод первых " + jewelryList.size() + " украшений для страны:");
        
        if (!jewelryList.isEmpty()) {
            Country country = jewelryList.get(0).getCountry();
            System.out.println("\nСтрана: " + country.getName() + " (" + country.getCode() + ")");
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-10s | %-15s | Тип%n",
            "ID", "Название", "Цена", "Производитель");
        System.out.println("-------------------------------------------------------------");

        for (Jewelry jewelry : jewelryList) {
            System.out.printf("%-5d | %-20s | %-10.2f | %-15s | %s%n",
                jewelry.getId(),
                jewelry.getName(),
                jewelry.getPrice(),
                jewelry.getManufacturer(),
                jewelry.getType().getName());
        }
        
        System.out.println("\nОбщее количество украшений для страны: " + jewelryList.size());
    }
}
*/