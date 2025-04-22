package edu.sfu.lab6.controllers;

import edu.sfu.lab6.model.Material;
import edu.sfu.lab6.services.MaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/gemstones")
    public List<Material> getGemstones() {
        return materialService.getMaterialsByType("gemstone");
    }

    @GetMapping("/metals")
    public List<Material> getMetals() {
        return materialService.getMaterialsByType("metal");
    }
}