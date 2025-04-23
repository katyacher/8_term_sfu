package edu.sfu.lab6.model;


public class CustomClass {
    private Integer jewelryId;
    private String jewelryName;
    private Double price;
    private String countryName;
    private String countryCode;
    
    // Конструкторы, геттеры и сеттеры
    public CustomClass() {}
    
    public CustomClass(Integer jewelryId, String jewelryName, Double price, 
                           String countryName, String countryCode) {
        this.jewelryId = jewelryId;
        this.jewelryName = jewelryName;
        this.price = price;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }
    
    // Геттеры и сеттеры для всех полей
    public Integer getJewelryId() { return jewelryId; }
    public void setJewelryId(Integer jewelryId) { this.jewelryId = jewelryId; }
    
    public String getJewelryName() { return jewelryName; }
    public void setJewelryName(String jewelryName) { this.jewelryName = jewelryName; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
}