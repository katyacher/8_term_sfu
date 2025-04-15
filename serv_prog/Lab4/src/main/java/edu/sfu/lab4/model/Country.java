package edu.sfu.lab4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter @Setter // Аннотации Lombok
public class Country {
    @Id
    private Integer id;
    private String name;
    private String code;

    // Геттеры и сеттеры
}