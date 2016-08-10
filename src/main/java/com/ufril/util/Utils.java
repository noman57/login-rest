package com.ufril.util;

import com.google.maps.api.GoogleMapApiClient;
import com.google.maps.distance.DistResult;
import com.google.maps.location.AddressComponents;
import com.google.maps.location.GeoResult;
import com.ufril.dto.common.Message;
import com.ufril.enumeration.StatusType;
import com.ufril.exception.BadRequestException;
import com.ufril.persistence.domain.common.Address;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
/**
 * Created by Noman
 */
public class Utils {

    private static Logger logger = Logger.getLogger(Utils.class);

    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_NUM_SPECIAL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_+";

    public static Pageable buildPageSpecification(Integer page, Integer size) {
        //return new PageRequest(pageIndex, pageSize, sortByLastNameAndFirstNameDesc());
        if ((page == null)) {
            page = 0;
        } else if (page >= 1) {
            page = page - 1;
        } else {
            page = 0;
        }

        if (size == null) {
            size = 10;
        } else if (size < 1) {
            size = 10;
        }
        return new PageRequest(page, size);
    }

    public static Message buildMessage(MessageSource messageSource, int code, String key, Locale locale) {
        Message message = new Message();
        message.setCode(code);
        message.setMessage(messageSource.getMessage(key, null, locale));
        return message;
    }



    /**
     * Get an AlphaNumeric String of length 'len'
     * @param len
     * @return
     */
    public static String getAlphaNumeric(final int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0;  i < len;  i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM.length());
            sb.append(ALPHA_NUM.charAt(ndx));
        }
        return sb.toString();
    }

    /**
     * Get an AlphaNumeric Special character String of length 'len'
     * @param len
     * @return
     */
    public static String getRandomValue(final int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0;  i < len;  i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM_SPECIAL.length());
            sb.append(ALPHA_NUM_SPECIAL.charAt(ndx));
        }
        return sb.toString();
    }

    public static double getMileFromKilometer(double kilometer) {
        return (kilometer * 0.62137);
    }

    public static double roundOff2(Double value) {
        //return Math.round(value * 100.0) / 100.0;
        //return new BigDecimal(value).round(new MathContext(5)).doubleValue();
        return new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String getBaseUrl() {
        String baseUrl;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        // System.out.println(baseUrl);
        return baseUrl;
    }

    private String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
    }

    /*
     address format
     street_number + route = street address
     locality = city
     administrative_area_level_1 = State
     country = country
     postal_code = zip
     formatted address = street address, city, state zip, country
     */
    public static Address buildAddress(GoogleMapApiClient googleMapApiClient, MessageSource messageSource, Locale locale, Address address) {

        StringBuilder addressBuilder = new StringBuilder();
        String comma = "";
        if (StringUtils.hasText(address.getStreetAddress())) {
            addressBuilder.append(address.getStreetAddress());
            comma =", ";
        }
        if (StringUtils.hasText(address.getCity())) {
            addressBuilder.append(comma);
            addressBuilder.append(address.getCity());
        }
        if (StringUtils.hasText(address.getCountry())) {
            addressBuilder.append(comma);
            addressBuilder.append(address.getCountry());
        }
        if (StringUtils.hasText(address.getZipCode())) {
            comma = " ";
            addressBuilder.append(comma);
            addressBuilder.append(address.getZipCode());
        }
        logger.debug("Build Address : " + addressBuilder.toString());
        if (StringUtils.hasText(addressBuilder.toString())) {
            GeoResult geoResult = googleMapApiClient.getLocation(addressBuilder.toString());
            if (StatusType.OK.toString().equals(geoResult.getStatus())) {
                StringBuilder streetAddress = new StringBuilder();
                for (AddressComponents addressComponents : geoResult.getResults()[0].getAddress_components()) {
                    if (addressComponents.getTypes().length != 0) {
                        if (addressComponents.getTypes()[0].equals("street_number")) {
                            streetAddress.append(addressComponents.getLong_name());
                        }
                        if (addressComponents.getTypes()[0].equals("route")) {
                            streetAddress.append(" ");
                            streetAddress.append(addressComponents.getLong_name());
                        }
                        if (addressComponents.getTypes()[0].equals("locality")) {
                            address.setCity(addressComponents.getLong_name());
                        }
                        if (addressComponents.getTypes()[0].equals("administrative_area_level_1")) {
                            address.setState(addressComponents.getShort_name());
                        }
                        if (addressComponents.getTypes()[0].equals("country")) {
                            address.setCountry(addressComponents.getShort_name());
                        }
                        if (addressComponents.getTypes()[0].equals("postal_code")) {
                            address.setZipCode(addressComponents.getLong_name());
                        }
                    } else {
                        streetAddress.append(addressComponents.getLong_name());
                    }
                }
                address.setStreetAddress(streetAddress.toString().trim());
                address.setLatitude(geoResult.getResults()[0].getGeometry().getLocation().getLat());
                address.setLongitude(geoResult.getResults()[0].getGeometry().getLocation().getLng());
                address.setFormatedAddress(geoResult.getResults()[0].getFormatted_address());
            } else {
                throw new BadRequestException(messageSource.getMessage("message.invalidAddress", null, locale));
            }
        }
        return address;
    }

    public static Double calculateSystemSuggestedRidePrice(GoogleMapApiClient googleMapApiClient, String sourceAddress, String destinationAddress) {
        Double suggestedPrice = null;
        DistResult result = googleMapApiClient.getDistance(sourceAddress, destinationAddress);
        if (StatusType.OK.toString().equals(result.getStatus()) &&
                StatusType.OK.toString().equals(result.getRows()[0].getElements()[0].getStatus())) {
            Integer distanceInMeter = result.getRows()[0].getElements()[0].getDistance().getValue();
            logger.debug("Distance In Meter : "+ distanceInMeter);
            Double distanceInMile = getMileFromKilometer(distanceInMeter/1000.0);
            suggestedPrice = distanceInMile * 0.30;
        }
        return suggestedPrice;
    }
}
