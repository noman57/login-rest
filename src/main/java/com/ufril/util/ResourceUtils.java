package com.ufril.util;

import com.ufril.exception.BadRequestException;
import com.ufril.exception.ResourceNotFoundException;
import com.ufril.persistence.domain.account.User;
import com.ufril.persistence.service.*;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by Noman
 */
public class ResourceUtils {


    public static void userExist(UserService userService, MessageSource messageSource, String username, Locale locale) {
        if (userService.isUserExists(username)) {
            throw new BadRequestException(messageSource.getMessage("message.userExist", null, locale) + username);
        }
    }

    public static void userNotExist(UserService userService, MessageSource messageSource, String userID, Locale locale) {
        User user = userService.getUserByUserNameOrEmail(userID);
        if (user == null) {
            throw new ResourceNotFoundException(messageSource.getMessage("message.userNotExist", null, locale) + userID);
        }
    }

    public static void phoneExist(UserService userService, MessageSource messageSource, String phone, Locale locale) {
        if (userService.isUserPhoneExists(phone)) {
            throw new BadRequestException(messageSource.getMessage("message.phoneExist", null, locale) + phone);
        }
    }

    public static void emailExist(UserService userService, MessageSource messageSource, String email, Locale locale) {
        if (userService.isUserEmailExists(email)) {
            throw new BadRequestException(messageSource.getMessage("message.emailExist", null, locale) + email);
        }
    }

    public static void emailNotExist(UserService userService, MessageSource messageSource, String email, Locale locale) {
        // FIXME we might need to check the enabled field to check if the user is enabled or disabled
        if (!userService.isUserEmailExists(email)) {
            throw new BadRequestException(messageSource.getMessage("message.emailNotExist", null, locale) + email);
        }
    }

  

    public static String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
    }


}
