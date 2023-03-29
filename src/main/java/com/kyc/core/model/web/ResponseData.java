package com.kyc.core.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
    @JsonIgnore
    private HttpHeaders httpHeaders;
    private MessageData error;

    public HttpStatus getHttpStatus(){

        if(httpStatus==null){
            httpStatus = HttpStatus.OK;
        }
        return httpStatus;
    }

    public static <T> ResponseData<T> of(T data){

        return of(data,HttpStatus.OK);
    }

    public static <T> ResponseData<T> of(T data, HttpStatus httpStatus){

        return of(data,null,httpStatus);
    }

    public static <T> ResponseData<T> of(T data, HttpHeaders headers, HttpStatus httpStatus){

        return ResponseData.<T>builder()
                .data(data)
                .httpHeaders(headers)
                .httpStatus(httpStatus)
                .build();
    }

    public static <T> ResponseData<T> of(MessageData messageData, HttpStatus httpStatus){

        return of(null,null,messageData,httpStatus);
    }

    public static <T> ResponseData<T> of(T data, HttpHeaders headers, MessageData messageData, HttpStatus httpStatus){

        return ResponseData.<T>builder()
                .data(data)
                .httpHeaders(headers)
                .httpStatus(httpStatus)
                .error(messageData)
                .build();
    }

    public static <T> ResponseData<T> emptyResponse(){

        return ResponseData.<T>builder()
                .build();
    }


    public ResponseEntity<ResponseData<T>> toResponseEntity(){

        return ResponseEntity.status(getHttpStatus()).headers(getHttpHeaders()).body(this);
    }

    public ResponseEntity<Resource> toResponseEntityStream(){

        T data = getData();
        if(data instanceof Resource){
            Resource resource = (Resource) getData();
            return ResponseEntity.status(getHttpStatus())
                    .headers(getHttpHeaders())
                    .body(resource);
        }
        return ResponseEntity.status(getHttpStatus())
                .headers(getHttpHeaders())
                .build();
    }
}

