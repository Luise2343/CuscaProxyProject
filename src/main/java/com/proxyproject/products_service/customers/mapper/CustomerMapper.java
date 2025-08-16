package com.proxyproject.products_service.customers.mapper;

import com.proxyproject.products_service.customers.domain.Customer;
import com.proxyproject.products_service.customers.dto.CustomerCreateRequest;
import com.proxyproject.products_service.customers.dto.CustomerResponse;
import com.proxyproject.products_service.customers.dto.CustomerUpdateRequest;

public class CustomerMapper {

    public static Customer toEntity(CustomerCreateRequest r) {
        Customer c = new Customer();
        c.setFullName(r.fullName);
        c.setEmail(r.email);
        c.setPhone(r.phone);
        return c;
    }

    public static void updateEntity(Customer c, CustomerUpdateRequest r) {
        c.setFullName(r.fullName);
        c.setEmail(r.email);
        c.setPhone(r.phone);
        if (r.status != null) {
            c.setStatus(Customer.Status.valueOf(r.status.toUpperCase()));
        }
    }

    public static CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getFullName(),
                c.getEmail(),
                c.getPhone(),
                c.getStatus().name(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
}
