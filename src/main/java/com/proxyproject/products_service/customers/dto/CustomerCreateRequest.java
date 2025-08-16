package com.proxyproject.products_service.customers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerCreateRequest {
    @NotBlank @Size(max = 120)
    public String fullName;

    @NotBlank @Email @Size(max = 150)
    public String email;

    @Size(max = 30)
    public String phone;
}
