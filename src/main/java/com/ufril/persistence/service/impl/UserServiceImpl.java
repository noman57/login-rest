package com.ufril.persistence.service.impl;

import com.ufril.dto.account.CreateUserDTO;
import com.ufril.dto.account.GetProfileDTO;
import com.ufril.enumeration.RoleType;
import com.ufril.persistence.domain.account.Role;
import com.ufril.persistence.domain.account.User;
import com.ufril.persistence.domain.common.Address;
import com.ufril.persistence.repository.account.RoleRepository;
import com.ufril.persistence.repository.account.UserRepository;
import com.ufril.persistence.repository.common.AddressRepository;
import com.ufril.persistence.service.UserService;
import com.ufril.util.DateUtils;
import com.ufril.util.MapperUtils;
import com.ufril.util.Utils;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Noman
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    /*
	@Autowired
	private SubscriberService subscriberService;
    @Autowired
    private ReviewService reviewService;*/

    @Override
    public User createUser(CreateUserDTO userDTO, Address address) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setCreatedOn(new Date());
        user.setLastUpdatedOn(new Date());
        if (address != null) {
            address = addressRepository.save(address);
            user.setAddress(address);
        }

        User createdUser = userRepository.save(user);
        return createdUser;
    }

    @Override
    @Transactional(readOnly = true)
    public GetProfileDTO getProfile(String userID) {
        User user = userRepository.findByUsernameOrEmail(userID, userID);// findOne(username);
        GetProfileDTO profile = modelMapper.map(user, GetProfileDTO.class);
        if (user.getAddress() != null) {
            profile.setStreetAddress(user.getAddress().getStreetAddress());
            profile.setCity(user.getAddress().getCity());
            profile.setState(user.getAddress().getState());
            profile.setZipCode(user.getAddress().getZipCode());
        }
        return profile;
    }

  

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findOne(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserNameOrEmail(String userID) {
        return userRepository.findByUsernameOrEmail(userID, userID);
    }

  
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

   

    @Override
    @Transactional(readOnly = true)
    public boolean isUserExists(String username) {
        return userRepository.exists(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUserPhoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(RoleType roleType) {
        return roleRepository.findByName(roleType.name());
    }




}
