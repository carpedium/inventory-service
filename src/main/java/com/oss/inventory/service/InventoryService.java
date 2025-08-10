package com.oss.inventory.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oss.inventory.dto.DeviceInstanceDTO;
import com.oss.inventory.entity.DeviceInstance;
import com.oss.inventory.enums.DeviceStatus;
import com.oss.inventory.mapper.DeviceInstanceMapper;
import com.oss.inventory.repository.InventoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final DeviceInstanceMapper deviceInstanceMapper;

    public DeviceInstanceDTO getById(Long id) {
        log.info("Fetching device by ID: {}", id);
        return inventoryRepository.findById(id)
                .map(deviceInstanceMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("DeviceInstance not found: {}", id);
                    return new EntityNotFoundException("DeviceInstance not found: " + id);
                });
    }

    public Page<DeviceInstanceDTO> findByExample(DeviceInstanceDTO exampleDTO, Pageable pageable) {
        log.debug("Finding devices by example: {}, pageRequest: {}", exampleDTO, pageable);
        DeviceInstance probe = deviceInstanceMapper.toEntity(exampleDTO);
        Example<DeviceInstance> example = Example.of(probe);
        Page<DeviceInstance> result = inventoryRepository.findAll(example, pageable);
        log.info("Found {} matching devices", result.getTotalElements());
        return result.map(deviceInstanceMapper::toDTO);
    }

    public void deleteById(Long id) {
        log.warn("Deleting device with ID: {}", id);
        inventoryRepository.deleteById(id);
        log.info("Device deleted: ID {}", id);
    }

    public DeviceInstanceDTO create(DeviceInstanceDTO dto) {
        log.info("Creating device with data: {}", dto);
        DeviceInstance entity = deviceInstanceMapper.toEntity(dto);

        if (dto.getUsedForId() != null) {
            log.debug("Setting usedFor ID: {}", dto.getUsedForId());
            entity.setUsedFor(dto.getUsedForId());
        }

        if (entity.getStatus() == null) {
            entity.setStatus(DeviceStatus.AVAILABLE);
            log.debug("Defaulting status to AVAILABLE");
        }

        DeviceInstance saved = inventoryRepository.save(entity);
        log.info("Device created successfully with ID: {}", saved.getId());
        return deviceInstanceMapper.toDTO(saved);
    }

    public DeviceInstanceDTO findByExample(DeviceInstanceDTO exampleDTO) {
        log.debug("Finding top match for device example: {}", exampleDTO);
        DeviceInstance probe = deviceInstanceMapper.toEntity(exampleDTO);
        Example<DeviceInstance> example = Example.of(probe);
        List<DeviceInstance> result = inventoryRepository.findAll(example);

        if (result.isEmpty()) {
            log.warn("No match found for given example: {}", exampleDTO);
            throw new EntityNotFoundException("No device matching given example.");
        }

        log.info("Top match found: ID {}", result.get(0).getId());
        return deviceInstanceMapper.toDTO(result.get(0));
    }

    public void save(DeviceInstanceDTO deviceDto) {
        log.info("Updating device with ID: {}", deviceDto.getId());
        DeviceInstance device = inventoryRepository.findById(deviceDto.getId())
                .orElseThrow(() -> {
                    log.warn("Device not found for update: {}", deviceDto.getId());
                    return new EntityNotFoundException("Device not found: " + deviceDto.getId());
                });

        device.setStatus(deviceDto.getStatus());
        device.setUsedFor(deviceDto.getUsedForId());

        inventoryRepository.save(device);
        log.info("Device updated: {}", deviceDto.getId());
    }
}
