package com.oss.inventory.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oss.inventory.entity.DeviceInstance;

@Repository
public interface InventoryRepository extends JpaRepository<DeviceInstance, Long> {
    // Additional custom queries can be added later
}