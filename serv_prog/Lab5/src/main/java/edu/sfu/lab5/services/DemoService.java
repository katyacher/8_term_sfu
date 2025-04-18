package edu.sfu.lab5.services;

import edu.sfu.lab5.dao.*;
import edu.sfu.lab5.model.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DemoService {
    private final CountryDAO countryDAO = new CountryDAO();
    private final JewelryDAO jewelryDAO = new JewelryDAO();
    private final JewelryTypeDAO jewelryTypeDAO = new JewelryTypeDAO();
    private final MaterialDAO materialDAO = new MaterialDAO();

    public void demonstrateOneToMany() {
        // 1. Загружаем или создаем необходимые справочные данные
        initializeReferenceData();
        
        // 2. Создаем и сохраняем украшения
        List<Jewelry> createdJewelry = createAndSaveJewelries();
        
        // 3. Получаем и выводим результаты
        printResults(createdJewelry);
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

    private List<Jewelry> createAndSaveJewelries() {
        List<Jewelry> jewelries = new ArrayList<>();
        
        // Первое украшение (золотое кольцо)
        Jewelry goldRing = createJewelry(
            "Золотое кольцо",
            new BigDecimal("999.99"),
            new BigDecimal("5.75"),
            "Русские золотые изделия",
            1, // countryId (Россия)
            1, // typeId (Кольцо)
            Set.of(1) // materials (Золото)
        );
        jewelries.add(goldRing);

        // Второе украшение (кольцо с бриллиантом)
        Jewelry diamondRing = createJewelry(
            "Кольцо с бриллиантом",
            new BigDecimal("2500.00"),
            new BigDecimal("3.50"),
            "Tiffany & Co",
            1, // countryId (Россия)
            1, // typeId (Кольцо)
            Set.of(1, 2) // materials (Золото + Бриллиант)
        );
        jewelries.add(diamondRing);

        return jewelries;
    }

    private Jewelry createJewelry(String name, BigDecimal price, BigDecimal weight,
                                String manufacturer, Integer countryId,
                                Integer typeId, Set<Integer> materialIds) {
        // Создаем базовый объект
        Jewelry jewelry = new Jewelry();
        jewelry.setName(name);
        jewelry.setPrice(price);
        jewelry.setWeight(weight);
        jewelry.setManufacturer(manufacturer);

        // Устанавливаем страну
        Country country = countryDAO.getById(countryId);
        if (country == null) {
            throw new IllegalArgumentException("Страна с ID " + countryId + " не найдена");
        }
        jewelry.setCountry(country);

        // Устанавливаем тип
        JewelryType type = jewelryTypeDAO.getById(typeId);
        if (type == null) {
            throw new IllegalArgumentException("Тип украшения с ID " + typeId + " не найден");
        }
        jewelry.setType(type);

        // Устанавливаем материалы
        Set<Material> materials = materialIds.stream()
            .map(id -> {
                Material material = materialDAO.getById(id);
                if (material == null) {
                    throw new IllegalArgumentException("Материал с ID " + id + " не найден");
                }
                return material;
            })
            .collect(Collectors.toSet());
        jewelry.setMaterials(materials);

        // Сохраняем в БД
        Integer id = jewelryDAO.save(jewelry);
        jewelry.setId(id);

        return jewelry;
    }

    private void printResults(List<Jewelry> jewelryList) {
        System.out.println("\n=== Результаты демонстрации ===");
        
        if (jewelryList.isEmpty()) {
            System.out.println("Ничего не найдено");
            return;
        }

        System.out.println("Создано украшений: " + jewelryList.size());
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-3s | %-20s | %-8s | %-6s | %-15s | %-10s | Материалы%n",
            "ID", "Название", "Цена", "Вес", "Производитель", "Тип");
        System.out.println("-------------------------------------------------------------");

        for (Jewelry jewelry : jewelryList) {
            String materials = jewelry.getMaterials().stream()
                .map(Material::getName)
                .collect(Collectors.joining(", "));
            
            System.out.printf("%-3d | %-20s | %-8.2f | %-6.2f | %-15s | %-10s | %s%n",
                jewelry.getId(),
                jewelry.getName(),
                jewelry.getPrice(),
                jewelry.getWeight(),
                jewelry.getManufacturer(),
                jewelry.getType().getName(),
                materials);
        }
    }

    public static void main(String[] args) {
        DemoService demoService = new DemoService();
        demoService.demonstrateOneToMany();
    }
}