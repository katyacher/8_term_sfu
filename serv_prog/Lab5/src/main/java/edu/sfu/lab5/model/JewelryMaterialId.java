package edu.sfu.lab5.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class JewelryMaterialId implements Serializable {
    private static final long serialVersionUID = 1L;  // Добавлен serialVersionUID
    
    @Column(name = "jewelry_id")
    private Integer jewelryId;
    
    @Column(name = "material_id")
    private Integer materialId;
}