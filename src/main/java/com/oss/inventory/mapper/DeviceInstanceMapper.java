package com.oss.inventory.mapper;

import com.oss.inventory.dto.DeviceInstanceDTO;
import com.oss.inventory.entity.DeviceInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * MapStruct mapper interface for mapping between DeviceInstance entity and its
 * DTO. Handles flattening of nested UsedOrder object into primitive DTO fields.
 */
@Mapper(componentModel = "spring") // Enables Spring to detect and inject this mapper as a bean
public interface DeviceInstanceMapper {

	/**
	 * Converts DeviceInstance entity to DeviceInstanceDTO. Extracts usedFor.id and
	 * usedFor.orderReference into flat fields.
	 */
	@Mappings({ @Mapping(source = "usedFor.id", target = "usedForId"),
			@Mapping(source = "usedFor.orderReference", target = "usedForRef") })
	DeviceInstanceDTO toDTO(DeviceInstance entity);

	/**
	 * Converts DeviceInstanceDTO back to DeviceInstance entity. Ignores usedFor
	 * because it's set manually in service.
	 */
	@Mapping(target = "usedFor", ignore = true)
	DeviceInstance toEntity(DeviceInstanceDTO dto);
}
