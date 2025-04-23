package edu.sfu.lab6.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sfu.lab6.model.CustomClass;
import edu.sfu.lab6.services.CustomClassService;

@RestController
@RequestMapping("/api")
public class CustomClassController {

    @Autowired
    private  CustomClassService  customClassService;

    @GetMapping("/jewelry-countries")
    public ResponseEntity<List< CustomClass>> getCombinedData() {
        List< CustomClass> result =  customClassService.getCombinedData();
        return ResponseEntity.ok(result);
    }
}
