package com.idshorten.exception;

public class CustomAliasAlreadyExists extends Exception {

    public CustomAliasAlreadyExists(String alias) {
        super(alias);
    }
}
