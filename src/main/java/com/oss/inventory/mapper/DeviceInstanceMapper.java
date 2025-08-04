package com.oss.inventory.mapper;

import org.springframework.stereotype.Component;

import com.oss.inventory.dto.DeviceInstanceDTO;
import com.oss.inventory.entity.DeviceInstance;

@Component
public class DeviceInstanceMapper {

	public DeviceInstanceDTO toDTO(DeviceInstance entity) {
		if (entity == null)
			return null;

		return DeviceInstanceDTO.builder().id(entity.getId()).model(entity.getModel())
				.deviceType(entity.getDeviceType()).status(entity.getStatus())
				.usedForId(entity.getUsedFor())
				.build();
	}

	public DeviceInstance toEntity(DeviceInstanceDTO dto) {
		if (dto == null)
			return null;

		DeviceInstance.DeviceInstanceBuilder builder = DeviceInstance.builder()
				//.id(dto.getId())
				.model(dto.getModel())
				.deviceType(dto.getDeviceType()).status(dto.getStatus());

		// UsedFor is handled manually elsewhere (as in your service), so skip it here
		return builder.build();
	}
}

/**
 * MapStruct mapper interface for mapping between DeviceInstance entity and its
 * DTO. Handles flattening of nested UsedOrder object into primitive DTO fields.
 */
/*
 * @Mapper(componentModel = "spring") // Enables Spring to detect and inject
 * this mapper as a bean public interface DeviceInstanceMapper {
 * 
 *//**
	 * Converts DeviceInstance entity to DeviceInstanceDTO. Extracts usedFor.id and
	 * usedFor.orderReference into flat fields.
	 */
/*
 * @Mappings({ @Mapping(source = "usedFor.id", target = "usedForId"),
 * 
 * @Mapping(source = "usedFor.orderReference", target = "usedForRef") })
 * DeviceInstanceDTO toDTO(DeviceInstance entity);
 * 
 *//**
	 * Converts DeviceInstanceDTO back to DeviceInstance entity. Ignores usedFor
	 * because it's set manually in service.
	 *//*
		 * @Mapping(target = "usedFor", ignore = true) DeviceInstance
		 * toEntity(DeviceInstanceDTO dto); }
		 */