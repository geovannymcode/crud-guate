package com.geovannycode.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.numberPhone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.profession) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.gender) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.pictureUrl) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY c.id DESC")
    List<Customer> findByCriteria(@Param("searchTerm") String searchTerm);
}
