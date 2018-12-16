package com.bidproject.service.bidserver.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectExpiredException extends Exception {
    public ProjectExpiredException(String errorMessage){
        super(errorMessage);
    }
}
