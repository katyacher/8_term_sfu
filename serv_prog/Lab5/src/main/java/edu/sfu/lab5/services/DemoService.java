package edu.sfu.lab5.services;

import edu.sfu.lab5.dao.*;
import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;


public class DemoService {
    private final CountryDAO countryDAO = new CountryDAO();
    private final JewelryDAO jewelryDAO = new JewelryDAO();
    private final JewelryTypeDAO jewelryTypeDAO = new JewelryTypeDAO();
    private final MaterialDAO materialDAO = new MaterialDAO();

    public void demonstrateOneToMany(int limit) {
    	try {
            DAO.begin();
	    	// 1. Загружаем или создаем необходимые справочные данные
	        initializeReferenceData(); 
	        // 2. Получаем и выводим первые N записей
	        List<Jewelry> jewelryList = jewelryDAO.findFirstNByCountryId(1, limit);
	        printResults(jewelryList); 
	        DAO.commit();
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
	        
    }
    
    /*   
    public void demonstrateOneToMany() {
    	// 1. Загружаем или создаем необходимые справочные данные
        initializeReferenceData(); 
        // 2. Создаем и сохраняем украшения
        List<Jewelry> createdJewelry = createAndSaveJewelries(); 
        // 3. Получаем и выводим результаты
        printResults(createdJewelry, 0, createdJewelry.size()); // Выводим все записи
    }
    

    @SuppressWarnings("resource")
	public void demonstrateOneToManyWithDbPagination(int pageSize) {
    	 try {
             DAO.begin();
	    	// 1. Загружаем или создаем необходимые справочные данные
	        initializeReferenceData();
	       // 2. Создаем украшения
	        createAndSaveJewelries();
	        
	        int page = 0;
	        boolean hasMore = true;
	        
	        while (hasMore) {
	        	// 3. Получаем и выводим результаты
	            List<Jewelry> pageData = jewelryDAO.findByCountryIdWithPagination(1, page, pageSize);
	            
	            if (pageData.isEmpty()) {
	                hasMore = false;
	            } else {
	                printResults(pageData, page, pageSize);
	                page++;
	                
	                if (!pageData.isEmpty() && pageData.size() == pageSize) {
	                    System.out.println("\nНажмите Enter для следующей страницы...");
	                    new Scanner(System.in).nextLine();
	                }
	            }
	        }
	        DAO.commit();
         } catch (Exception e) {
             DAO.rollback();
             throw e;
         }
    }
*/
    
    private void initializeReferenceData() {
    	 try {
             DAO.begin();
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
	        DAO.commit();
         } catch (Exception e) {
             DAO.rollback();
             throw e;
         }
    }

    private List<Jewelry> createAndSaveJewelries() {
    	try {
              DAO.begin();
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
	
	        DAO.commit();
            return jewelries;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
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
/*
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
   
 // вывод с пагинацией
    private void printResults(List<Jewelry> jewelryList, int page, int pageSize) {
        System.out.println("\n=== Результаты демонстрации ===");
        
        if (jewelryList.isEmpty()) {
            System.out.println("Ничего не найдено");
            return;
        }

        // Рассчитываем границы пагинации
        int start = page * pageSize;
        int end = Math.min(start + pageSize, jewelryList.size());
        
        if (start >= jewelryList.size()) {
            System.out.println("Нет данных для отображения на странице " + (page + 1));
            return;
        }

        System.out.printf("Страница %d (записи %d-%d из %d):\n", 
            page + 1, start + 1, end, jewelryList.size());
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-3s | %-20s | %-8s | %-6s | %-15s | %-10s | Материалы%n",
            "ID", "Название", "Цена", "Вес", "Производитель", "Тип");
        System.out.println("-------------------------------------------------------------");

        for (int i = start; i < end; i++) {
            Jewelry jewelry = jewelryList.get(i);
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
    
    //Лимитированный вывод записей
    private void printResults(List<Jewelry> jewelryList) {
        System.out.println("\n=== Результаты демонстрации (первые " + jewelryList.size() + " записей) ===");
        
        if (jewelryList.isEmpty()) {
            System.out.println("Ничего не найдено");
            return;
        }

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
     */
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


