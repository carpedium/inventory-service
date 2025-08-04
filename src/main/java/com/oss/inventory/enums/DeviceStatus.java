package com.oss.inventory.enums;

public enum DeviceStatus {
    AVAILABLE,   // Free to allocate
    RESERVED,    // Temporarily blocked for an order
    ACTIVE,      // Actively used in an order
    INACTIVE     // Previously used, now idle
}
