package com.oss.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oss.inventory.dto.DeviceInstanceDTO;
import com.oss.inventory.entity.DeviceInstance;
import com.oss.inventory.entity.UsedOrder;
import com.oss.inventory.enums.DeviceStatus;
import com.oss.inventory.mapper.DeviceInstanceMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private DeviceInstanceMapper deviceInstanceMapper;

	public DeviceInstanceDTO getById(Long id) {
		return inventoryRepository.findById(id).map(deviceInstanceMapper::toDTO)
				.orElseThrow(() -> new EntityNotFoundException("DeviceInstance not found: " + id));
	}

	public Page<DeviceInstanceDTO> findByExample(DeviceInstanceDTO exampleDTO, Pageable pageable) {
		DeviceInstance probe = deviceInstanceMapper.toEntity(exampleDTO);
		Example<DeviceInstance> example = Example.of(probe);
		return inventoryRepository.findAll(example, pageable).map(deviceInstanceMapper::toDTO);
	}

	public void deleteById(Long id) {
		inventoryRepository.deleteById(id);
	}

	public DeviceInstanceDTO create(DeviceInstanceDTO dto) {
		DeviceInstance entity = deviceInstanceMapper.toEntity(dto);

		if (dto.getUsedForId() != null) {
			entity.setUsedFor(UsedOrder.builder().id(dto.getUsedForId()).orderReference(dto.getUsedForRef()).build());
		}

		if (entity.getStatus() == null) {
			entity.setStatus(DeviceStatus.AVAILABLE);
		}

		return deviceInstanceMapper.toDTO(inventoryRepository.save(entity));
	}
}