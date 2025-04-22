package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.JewelryDAO;
import edu.sfu.lab6.model.Jewelry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class JewelryService {

    private final JewelryDAO jewelryDAO;

    @Autowired
    public JewelryService(JewelryDAO jewelryDAO) { // конструктор с внедрением зависимостей
        this.jewelryDAO = jewelryDAO;
    }

    public List<Jewelry> searchJewelry(String name,
                                     BigDecimal minPrice,
                                     BigDecimal maxPrice,
                                     Integer typeId,
                                     String manufacturer) {
        return jewelryDAO.findWithFilters(
            name, minPrice, maxPrice, typeId, manufacturer, 0, 20);
    }

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

	public List<Object[]> findExpensiveJewelry(int firstResult, int maxResults) {
		return jewelryDAO.findExpensiveGemstoneJewelry(firstResult, maxResults);
	}
}