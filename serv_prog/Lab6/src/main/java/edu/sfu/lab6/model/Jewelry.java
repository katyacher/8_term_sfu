package edu.sfu.lab6.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "jewelry")
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "price", nullable = false, columnDefinition = "numeric(10,2)")
    private BigDecimal price;

    @Column(name = "weight", nullable = false, columnDefinition = "numeric(8,2)")
    private BigDecimal weight;
    
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private JewelryType type;
    
    @Column(name = "manufacturer", nullable = false, length = 100)
    private String manufacturer;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    
    @ManyToMany(fetch = FetchType.EAGER) // Измените LAZY на EAGER
    @JoinTable(
        name = "jewelryMaterial",
        joinColumns = @JoinColumn(name = "jewelry_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private Set<Material> materials;
    
 // Геттеры
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public JewelryType getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Country getCountry() {
        return country;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setType(JewelryType type) {
        this.type = type;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }

}