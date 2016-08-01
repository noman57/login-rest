package com.ufril.util;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noman
 */
public class MapperUtils {

    /**
     * map a list object to a new list object
     * @param sources
     * @param destinationType
     * @param mapper
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T,S> List<T> mapAll(List<S> sources, Class<T> destinationType, ModelMapper mapper) {
        final List<T> destinations = new ArrayList<T>();
        for (S source : sources) {
            destinations.add(mapper.map(source, destinationType));
        }
           return destinations;
    }


    
   
    




 
}
