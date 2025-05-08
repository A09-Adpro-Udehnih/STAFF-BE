package com.example.staffbe.controller;

import com.example.staffbe.strategy.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/api/resources")
    public List<?> getAll(@RequestParam String type) {
        return resourceService.getAll(type);  // Memanggil service untuk mendapatkan data sesuai type
    }
}
