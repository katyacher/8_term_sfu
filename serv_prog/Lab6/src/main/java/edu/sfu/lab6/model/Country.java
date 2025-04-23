package edu.sfu.lab6.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Страна производитель")
@Data // для автоматической генерации геттеров/сеттеров и других методов
@Entity
@Table(name = "Сountry")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор страны", example = "1")
    private Integer id;
    
    @Column(name = "name", nullable = false, unique = true)
    @Schema(description = "Название страны", example = "Россия")
    private String name;
    
    @Column(name = "code", nullable = false, unique = true, length = 2)
    @Schema(description = "Код страны (2 символа)", example = "RU")
    private String code;

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
      
}
