package com.example.eMarketplace.Controller;

import com.example.eMarketplace.DTO.ItemDTO;
import com.example.eMarketplace.Service.ItemService;
import com.example.eMarketplace.DTO.ItemDTO;
import com.example.eMarketplace.Service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/market")
public class eMarketplaceController {

    private final ItemService itemService;

    public eMarketplaceController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> getAllItems(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(itemService.getItems(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<String> createItem(
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam("description") String description,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            itemService.saveItem(name, price, description, photo);
            return ResponseEntity.ok("Item created successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }
}