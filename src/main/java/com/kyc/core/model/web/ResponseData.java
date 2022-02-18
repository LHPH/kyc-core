package com.kyc.core.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ResponseData<T> {

    private T data;
    @JsonIgnore
    private HttpStatus httpStatus;
    private MessageData error;

    public HttpStatus getHttpStatus(){

        if(httpStatus==null){
            httpStatus = HttpStatus.OK;
        }
        return httpStatus;
    }

    public static <T> ResponseData<T> of(T data){

        return ResponseData.<T>builder()
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> ResponseData<T> of(T data, HttpStatus httpStatus){

        return ResponseData.<T>builder()
                .data(data)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ResponseData<T> of(MessageData messageData, HttpStatus httpStatus){

        return ResponseData.<T>builder()
                .data(null)
                .httpStatus(httpStatus)
                .error(messageData)
                .build();
    }


    public ResponseEntity<ResponseData<T>> toResponseEntity(){

        return ResponseEntity.status(getHttpStatus()).body(this);
    }
}

