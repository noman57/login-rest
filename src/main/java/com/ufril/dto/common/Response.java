package com.ufril.dto.common;

import com.ufril.enumeration.StatusType;

/**
 * Created by Noman 
 */
public class Response {

    private final StatusType status;
    private final Object data;
    private final Error error;


    public Response(StatusType status, Object data) {
        this(status, data, null);
    }

    public Response(StatusType status, Object data, Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public StatusType getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public Error getError() {
        return error;
    }
}
