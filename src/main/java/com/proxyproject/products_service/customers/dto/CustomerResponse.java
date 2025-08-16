package com.proxyproject.products_service.customers.dto;

import java.time.Instant;

public record CustomerResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}
