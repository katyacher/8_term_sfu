package edu.sfu.lab4;

import edu.sfu.lab4.services.TestSrvs;

public class Main {
    public static void main(String[] args) {
        // Тест получения имени
        String countryName = TestSrvs.getName(1);
        System.out.println("Country name: " + countryName);

        // Тест получения диапазона
        System.out.println("Names in range: " + TestSrvs.getNamesInRange(1, 3));

        // Тест создания записи
        int created = TestSrvs.createCountry("Canada", "CA");
        System.out.println("Created rows: " + created);
    }
}