package com.ufril.persistence.repository.common;

import com.ufril.persistence.domain.common.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Noman
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
