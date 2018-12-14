package com.bidproject.service.bidserver.seller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SellerNotFoundException extends Exception {
    public SellerNotFoundException (String errorMessage){
        super(errorMessage);
    }
}
