package org.khazar.SpringSecurity.response;

import org.springframework.http.HttpStatus;

public interface ResponseMessages {
     String message();
     String key();
     HttpStatus status();
}
