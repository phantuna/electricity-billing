package com.example.electricity.exception;

public enum ErrorCode {
    USER_EXISTED(1001,"User already exists"),
    USER_NOT_FOUND(1002,"User not found"),
    USER_ROLE_NOT_FOUND(1003,"User role not found"),

    ROLE_NOT_FOUND(2001,"Role not found"),
    ROLE_EXISTED(2002,"Role already exists"),

    ELECTRICITY_EXISTED(3001,"Electricity already exists"),
    CURRENTkWH_NOT_GREATER_THAN_PREVIOUSkWH(3002,"Current kwh not greater than previous kwh"),
    
    JWT_NOT_CREATED(4000,"Cannot create JWT"),
    INVALID_SIGNATURE(4001,"Invalid signature"),
    INVALID_TOKEN(4002,"Invalid token"),
    MISSING_TOKEN(4003,"Missing token"),
    INVALID_PASSWORD(4004,"Invalid password"),

    UNAUTHENTICATED(5000,"Unauthenticated"),//ko dang nhap

    INVALID_PERMISSIONS(6001,"Cannot parse permissions from token"),
    PERMISSION_NOT_FOUND(6002,"Permission not found"),
    
    USERNAME_NOT_VALID(7001,"username must be at least 4 charactors"),
    PASSWPRD_NOT_VALID(7002,"password must be at least 8 charactors"),
    ;


    ErrorCode(int code ,String message){
        this.code = code;
        this.message = message;
    }
    private Integer code;
    private String message;
    
    public Integer getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
}