package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.MaterialDAO;
import edu.sfu.lab6.model.Material;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MaterialService {

    private final MaterialDAO materialDAO;

    public MaterialService(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    public List<Material> getAllMaterials() {
        return materialDAO.getAll();
    }

    public List<Material> getMaterialsByType(String type) {
        return materialDAO.findByType(type);
    }

    public Material createMaterial(Material material) {
        Integer id = materialDAO.save(material);
        return materialDAO.getById(id);
    }
}