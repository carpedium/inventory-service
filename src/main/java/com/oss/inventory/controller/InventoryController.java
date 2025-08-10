package com.oss.inventory.controller;

import com.oss.inventory.dto.DeviceInstanceDTO;
import com.oss.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        log.info("Received request to /hello");
        return ResponseEntity.ok("hello inventory");
    }

    @GetMapping("/get/{id}")
    public DeviceInstanceDTO getById(@PathVariable Long id) {
        log.info("Fetching device with ID: {}", id);
        DeviceInstanceDTO result = inventoryService.getById(id);
        log.info("Fetched device: {}", result);
        return result;
    }

    @PostMapping("/get")
    public Page<DeviceInstanceDTO> findByExample(
            @RequestBody DeviceInstanceDTO example,
            @PageableDefault(page = 0, size = 2) Pageable pageable) {

        log.info("Searching by example: {}, with pagination: {}", example, pageable);
        Page<DeviceInstanceDTO> page = inventoryService.findByExample(example, pageable);
        log.info("Found {} devices matching example", page.getTotalElements());
        return page;
    }

    @PostMapping("/getsingle")
    public ResponseEntity<DeviceInstanceDTO> findTopByExample(@RequestBody DeviceInstanceDTO example) {
        log.info("Finding top match for example: {}", example);
        DeviceInstanceDTO dto = inventoryService.findByExample(example);
        log.info("Top match found: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        log.warn("Deleting device with ID: {}", id);
        inventoryService.deleteById(id);
        log.info("Device deleted: ID {}", id);
    }

    @PostMapping("/createdevice")
    public DeviceInstanceDTO create(@RequestBody DeviceInstanceDTO dto) {
        log.info("Creating new device: {}", dto);
        DeviceInstanceDTO created = inventoryService.create(dto);
        log.info("Created device: {}", created);
        return created;
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody DeviceInstanceDTO deviceDto) {
        log.info("Updating device: {}", deviceDto);
        inventoryService.save(deviceDto);
        log.info("Update complete for device ID: {}", deviceDto.getId());
        return ResponseEntity.accepted().build();
    }
}
