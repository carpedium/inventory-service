package com.oss.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oss.inventory.dto.DeviceInstanceDTO;
import com.oss.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/get/{id}")
    public DeviceInstanceDTO getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @PostMapping("/get")
    public Page<DeviceInstanceDTO> findByExample(
    		@RequestBody DeviceInstanceDTO example, 
    		@PageableDefault(page=0, size=2) Pageable pageable) {
        return inventoryService.findByExample(example, pageable);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        inventoryService.deleteById(id);
    }

    @PostMapping("/createdevice")
    public DeviceInstanceDTO create(@RequestBody DeviceInstanceDTO dto) {
        return inventoryService.create(dto);
    }
}