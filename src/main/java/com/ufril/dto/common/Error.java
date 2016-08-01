package com.ufril.dto.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noman on 11/26/15.
 */
public class Error {

    private int status;
    private String title;
    private String detail;
    private long timeStamp;
    private String developerMessage;
//    private Map<String, List<ValidationError>> errors = new HashMap<>();
    private List<ValidationError> errors = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

//    public Map<String, List<ValidationError>> getErrors() {
//        return errors;
//    }

//    public void setErrors(Map<String, List<ValidationError>> errors) {
//        this.errors = errors;
//    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Error {");
        sb.append("status=").append(status);
        sb.append(", title='").append(title).append('\'');
        sb.append(", detail='").append(detail).append('\'');
        sb.append(", timeStamp=").append(timeStamp);
        sb.append(", developerMessage='").append(developerMessage).append('\'');
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }
}
