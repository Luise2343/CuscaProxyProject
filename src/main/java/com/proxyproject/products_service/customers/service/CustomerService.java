package com.proxyproject.products_service.customers.service;

import com.proxyproject.products_service.customers.domain.Customer;
import com.proxyproject.products_service.customers.dto.CustomerCreateRequest;
import com.proxyproject.products_service.customers.dto.CustomerResponse;
import com.proxyproject.products_service.customers.dto.CustomerUpdateRequest;
import com.proxyproject.products_service.customers.mapper.CustomerMapper;
import com.proxyproject.products_service.customers.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;

    @Transactional(readOnly = true)
    public Page<CustomerResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(CustomerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CustomerResponse get(Long id) {
        var c = repo.findById(id).orElseThrow(() -> notFound(id));
        return CustomerMapper.toResponse(c);
    }

    @Transactional
    public CustomerResponse create(CustomerCreateRequest r) {
        if (repo.existsByEmail(r.email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        var c = CustomerMapper.toEntity(r);
        c = repo.save(c);
        return CustomerMapper.toResponse(c);
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerUpdateRequest r) {
        var c = repo.findById(id).orElseThrow(() -> notFound(id));
        if (!c.getEmail().equalsIgnoreCase(r.email) && repo.existsByEmail(r.email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        CustomerMapper.updateEntity(c, r);
        return CustomerMapper.toResponse(c);
    }

    @Transactional
    public void delete(Long id) {
        var c = repo.findById(id).orElseThrow(() -> notFound(id));
        repo.delete(c);
    }

    private RuntimeException notFound(Long id) {
        return new RuntimeException("Customer " + id + " not found");
    }
}
