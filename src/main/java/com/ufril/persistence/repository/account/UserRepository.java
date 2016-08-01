package com.ufril.persistence.repository.account;

import com.ufril.persistence.domain.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author moin
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    User findByUsernameOrEmail(String username, String email);
}
