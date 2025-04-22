package edu.sfu.lab6.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "type", nullable = false, length = 20)
    private String type; // 'metal' или 'gemstone'
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "carat", columnDefinition = "numeric(5,2)")
    private BigDecimal carat;
    
    @Column(name = "quality", length = 50)
    private String quality;
    // Геттеры
    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCarat() {
        return carat;
    }

    public String getQuality() {
        return quality;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCarat(BigDecimal carat) {
        this.carat = carat;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

	@Override
	public int hashCode() {
		return Objects.hash(carat, id, name, quality, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		return Objects.equals(carat, other.carat) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(quality, other.quality) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", type=" + type + ", name=" + name + ", carat=" + carat + ", quality=" + quality
				+ "]";
	}
	
    
}