package com.example.sample;

import com.example.sample.customer.Customer;
import com.example.sample.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void save () {
        var saveCustomer = saveCustomer();
        assertEquals(saveCustomer.getTel(), CustomerExample.customer.getTel());
        assertEquals(saveCustomer.getName(), CustomerExample.customer.getName());
    }

    @Test
    void findById() {
        var saveCustomer = saveCustomer();
        var optional = this.repository.findById(saveCustomer.getId());
        assertTrue(optional.isPresent());
    }

    @Test
    void update () {
        var saveCustomer = saveCustomer();
        saveCustomer.setTel("01040234503");

        var updateCustomer = repository.save(saveCustomer);
        assertEquals(updateCustomer.getTel(), "01040234503");
    }

    @Test
    void delete () {
        var saveCustomer = saveCustomer();
        repository.delete(saveCustomer);
        assertTrue(repository.findById(saveCustomer.getId()).isEmpty());
    }

    private Customer saveCustomer() {
        return repository.save(CustomerExample.customer);
    }

}
