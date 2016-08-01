package com.ufril.dto.common;

import com.ufril.enumeration.StatusType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Noman
 */
public class PageResponse {

    private final StatusType status;
    private final List<?> content;
    private final Error error;


    public PageResponse(StatusType status, Page page) {
        this(status, page, null);
    }

    public PageResponse(StatusType status, Page page, Error error) {
        this.status = status;
        this.content = page.getContent();
        this.error = error;
    }

    public StatusType getStatus() {
        return status;
    }

    public List<?> getContent() {
        return content;
    }

    public Error getError() {
        return error;
    }
}
