package edu.sfu.lab6.controllers;

import edu.sfu.lab6.model.JewelryType;
import edu.sfu.lab6.services.JewelryTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jewelry-types")
public class JewelryTypeController {

    private final JewelryTypeService jewelryTypeService;

    public JewelryTypeController(JewelryTypeService jewelryTypeService) {
        this.jewelryTypeService = jewelryTypeService;
    }

    @GetMapping
    public List<JewelryType> getAllJewelryTypes() {
        return jewelryTypeService.getAllJewelryTypes();
    }

    @PostMapping
    public JewelryType createJewelryType(@RequestBody JewelryType jewelryType) {
        return jewelryTypeService.createJewelryType(jewelryType);
    }
}