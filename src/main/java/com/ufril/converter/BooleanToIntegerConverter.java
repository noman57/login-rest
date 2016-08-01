package com.ufril.converter;

import javax.persistence.AttributeConverter;

/**
 * Created by moin on 11/3/15.
 */
public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return dbData != null && (dbData > 0);
    }

}
