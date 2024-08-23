package com.capstone.users_service.repository;

import com.capstone.users_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    /**
     * Get list of addresses added by user.
     * @param userId
     * @return list of addresses of a user
     */
    List<Address> findById(long userId);
}
