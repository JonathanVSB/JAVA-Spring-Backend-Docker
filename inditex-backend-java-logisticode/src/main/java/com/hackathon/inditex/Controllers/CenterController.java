package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Services.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @PostMapping
    public ResponseEntity<String> createCenter(@RequestBody Center center) {
        centerService.createCenter(center);
        return ResponseEntity.status(201).body("Center created successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Center>> getAllCenters() {
        return ResponseEntity.ok(centerService.getAllCenters());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCenter(@PathVariable Long id, @RequestBody Center updatedCenter) {
        centerService.updateCenter(id, updatedCenter);
        return ResponseEntity.ok("Center updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return ResponseEntity.ok("Center deleted successfully.");
    }
}
