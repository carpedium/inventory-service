package com.oss.inventory.dto;

import com.oss.inventory.enums.DeviceStatus;
import com.oss.inventory.enums.DeviceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInstanceDTO {
    private Long id;
    private String model;
    private DeviceStatus status;
    private DeviceType deviceType;
    private Long usedForId;
    private String usedForRef;
}
