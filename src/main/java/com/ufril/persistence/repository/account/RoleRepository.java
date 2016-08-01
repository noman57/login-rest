package com.ufril.persistence.repository.account;

import com.ufril.persistence.domain.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moin on 11/3/15.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
