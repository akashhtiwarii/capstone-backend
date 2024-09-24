package com.capstone.users_service.repository;

import com.capstone.users_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for accessing the {@link Address} entity.
 * This interface provides methods to perform CRUD operations and custom queries
 * on the {@code address} table in the database.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Finds an {@link Address} entity by its unique identifier.
     *
     * @param addressId the ID of the address to be found
     * @return the {@link Address} entity with the specified ID, or {@code null} if no such entity exists
     */
    Address findById(long addressId);

    /**
     * Finds all {@link Address} entities associated with a given user ID.
     *
     * @param userId the ID of the user whose addresses are to be retrieved
     * @return a list of {@link Address} entities associated with the specified user ID,
     * or an empty list if no addresses are found
     */
    List<Address> findByUserId(long userId);
}
