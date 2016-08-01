package com.ufril.persistence.service;

import com.ufril.dto.account.CreateUserDTO;
import com.ufril.dto.account.GetProfileDTO;
import com.ufril.enumeration.RoleType;
import com.ufril.persistence.domain.account.Role;
import com.ufril.persistence.domain.account.User;
import com.ufril.persistence.domain.common.Address;

import java.util.Date;
import java.util.List;

/**
 * Created by Noman
 */
public interface UserService {

    User createUser(CreateUserDTO userData, Address address);

    GetProfileDTO getProfile(String userID);


    User getUserByUsername(String username);

    User getUserByEmail(String email);

    /**
     * It takes userID which can be username or email and return an user object if exist
     * @param userID
     * @return
     */
    User getUserByUserNameOrEmail(String userID);

   
    void saveUser(User user);

    
    boolean isUserExists(String username);

    boolean isUserEmailExists(String email);

    boolean isUserPhoneExists(String phone);

	Role getRoleByName(RoleType roleType);

  

}
