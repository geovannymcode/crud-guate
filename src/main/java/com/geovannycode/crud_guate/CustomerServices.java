package com.geovannycode.crud_guate;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CustomerServices {

    private final CustomerRepository customerRepository;

    public CustomerServices(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*public List<Customer> listAll(){
            return customerRepository.findAll();
    }*/

    public List<Customer> listAll(String searchTerm) {
        String searchInput = "%" + searchTerm.toLowerCase() + "%";
        return customerRepository.findByCriteria(searchInput);
    }


    public void createOrUpdate(Customer customer) {
        if (customer.getId() != null && customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);
        } else {
            customerRepository.save(customer);
        }
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
