package com.idshorten.enums;

public enum MappedErrorEnum {
    CUSTOM_ALIAS_ALREADY_EXISTS ("001") ,
    SHORTENED_URL_NOT_FOUND ("002") ;

    public String valueOfError;

    MappedErrorEnum(String value) {
        valueOfError = value;
    }

}
