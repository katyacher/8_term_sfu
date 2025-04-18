package edu.sfu.lab5.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jewelryMaterial")
public class JewelryMaterial {
    @EmbeddedId
    private JewelryMaterialId id;
    
    @ManyToOne
    @MapsId("jewelryId")
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;
    
    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;

	public JewelryMaterialId getId() {
		return id;
	}

	public void setId(JewelryMaterialId id) {
		this.id = id;
	}

	public Jewelry getJewelry() {
		return jewelry;
	}

	public void setJewelry(Jewelry jewelry) {
		this.jewelry = jewelry;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
    
    
}