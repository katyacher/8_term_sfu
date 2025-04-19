package edu.sfu.lab5.services;

import edu.sfu.lab5.dao.JewelryDAO;
import edu.sfu.lab5.manager.DAO;
import edu.sfu.lab5.model.Jewelry;
import java.math.BigDecimal;
import java.util.List;

public class JewelryService {
    private final JewelryDAO jewelryDAO;

    public JewelryService() {
        this.jewelryDAO = new JewelryDAO();
    }

    // Метод-обертка для поиска с фильтрами
    public List<Jewelry> searchJewelry(String name, 
                                     BigDecimal minPrice,
                                     BigDecimal maxPrice,
                                     Integer typeId,
                                     String manufacturer) {
        try {
            DAO.begin();
            // Стандартная пагинация (первые 20 записей)
            List<Jewelry> result = jewelryDAO.findWithFilters(
                name, minPrice, maxPrice, typeId, manufacturer, 0, 20);
            DAO.commit();
            return result;
        } catch (Exception e) {
            DAO.rollback();
            throw e;
        }
    }

    // Метод для вывода результатов (не требует транзакции)
    public void printJewelryList(List<Jewelry> jewelryList) {
        if (jewelryList.isEmpty()) {
            System.out.println("Ничего не найдено");
            return;
        }

        System.out.println("Найдено украшений: " + jewelryList.size());
        System.out.println("--------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-10s | %-8s | %-15s | %-20s%n",
            "ID", "Название", "Цена", "Вес", "Производитель", "Тип");
        System.out.println("--------------------------------------------------");

        for (Jewelry jewelry : jewelryList) {
            System.out.printf("%-5d | %-20s | %-10.2f | %-8.2f | %-15s | %-20s%n",
                jewelry.getId(),
                jewelry.getName(),
                jewelry.getPrice(),
                jewelry.getWeight(),
                jewelry.getManufacturer(),
                jewelry.getType().getName());
        }
    }
}