package com.capstone.users_service.repository;

import com.capstone.users_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * AddressRepository to connect with address table.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findById(long addressId);
    List<Address> findByUserId(long userId);
}
