package com.ufril.resource.v1;

import com.google.maps.api.GoogleMapApiClient;
import com.ufril.dto.account.*;
import com.ufril.dto.common.Message;
import com.ufril.dto.common.Response;
import com.ufril.enumeration.StatusType;
import com.ufril.exception.BadRequestException;
import com.ufril.helper.MessageHelper;
import com.ufril.helper.ResourceValidationHelper;
import com.ufril.persistence.domain.account.User;
import com.ufril.persistence.domain.common.Address;
import com.ufril.persistence.service.UserService;
import com.ufril.util.DateUtils;
import com.ufril.util.ResourceUtils;
import com.ufril.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author moin
 */
@RestController(value = "usersResourceV1")
@RequestMapping(value = {"/v1/", "/oauth2/v1/"})
@Api(value = "users", description = "User API")
public class UsersResource {

    private static Logger logger = Logger.getLogger(UsersResource.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GoogleMapApiClient googleMapApiClient;
    @Autowired
    private ResourceValidationHelper validationHelper;
    @Autowired
    private MessageHelper messageHelper;


    /**
     * Return the profile information of a user
     * Here userID can be username or email address
     * @param userID
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = "/users/{userID}",
            method = RequestMethod.GET
    )
    @ApiOperation(
            value = "Retrieves a user profile associated with the userID (username or email)",
            response = Response.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Response.class),
            @ApiResponse(code = 401, message = "There was an error with your E-Mail/Password combination. Please try again", response = Response.class),
            @ApiResponse(code = 404, message = "Unable to find User Profile", response = Response.class)
    })
    public ResponseEntity<?> getUserProfile(@PathVariable("userID") final String userID, Locale locale) {
//        ResourceUtils.userNotExist(userService, messageSource, userID, locale);
        // If a user exist with the userID the it will return the user otherwise throw ResourceNotFoundException
        validationHelper.isUserFound(userID, locale);
        GetProfileDTO profile = userService.getProfile(userID);
        return new ResponseEntity<>(new Response(StatusType.OK, profile), HttpStatus.OK);
    }

    /**
     * @param loginDTO
     * @param locale
     * @return
     */
    @RequestMapping(
            value = "/users/login",
            method = RequestMethod.POST)
    @ApiOperation(
            value = "Retrieves a user profile associated with the email",
            response = Response.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Response.class),
            @ApiResponse(code = 404, message = "Unable to find User Profile", response = Response.class)
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, Locale locale) {
        logger.debug("Inside Login resource method");
        // If a user exist with the userID the it will return the user otherwise throw ResourceNotFoundException
        User user = validationHelper.isUserFound(loginDTO.getUserId(), locale);
//        User user = userService.getUserByUserNameOrEmail(loginDTO.getUserId());
        GetProfileDTO profile;
        //if ((user != null) && (loginDTO.getPassword() != null)) {
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                profile = userService.getProfile(loginDTO.getUserId());
            } else {
               throw new BadRequestException(messageSource.getMessage("message.invalidCredentials", null, locale));
            }
        //} else {
        //    throw new ResourceNotFoundException(messageSource.getMessage("message.userNotExist", null, locale) + loginDTO.getUserId());
        //}
        return new ResponseEntity<>(new Response(StatusType.OK, profile), HttpStatus.OK);
    }


    /**
     * Create a new user
     *
     * @param userData
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = "/users",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(
            value = "Create a new User",
            response = Message.class,
            notes = "Following field are the required field to create a new user.<br> username<br> password<br> matchingPassword<br> email<br> firstName<br> lastName<br>")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User Created Successfully", response = Response.class),
            @ApiResponse(code = 500, message = "Error creating User", response = Response.class)
    })
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO userData, final Locale locale) {
        logger.debug("Inside createUser");
        //ResourceUtils.userExist(userService, messageSource, userData.getUsername(), locale);
        validationHelper.isUsernameAlreadyUsed(userData.getUsername(), locale);
        //ResourceUtils.emailExist(userService, messageSource, userData.getEmail(), locale);
        validationHelper.isEmailAlreadyUsed(userData.getEmail(), locale);
        if (StringUtils.hasText(userData.getPhone())) {
            //ResourceUtils.phoneExist(userService, messageSource, userData.getPhone(), locale);
            validationHelper.isPhoneNumberAlreadyUsed(userData.getPhone(), locale);
        }

         User newUser = userService.createUser(userData, null);
        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();
        responseHeaders.setLocation(newUserUri);
        Message message = messageHelper.buildMessage201("message.accountCreated", locale);
        return new ResponseEntity<>(new Response(StatusType.OK, message), responseHeaders, HttpStatus.CREATED);
    }

   






}
