package com.example.common.enums;

public enum MethodEnum {
    SET_CREATE_TIME("setCreateTime"),
    SET_UPDATE_TIME("setUpdateTime");
    public final String str;

    MethodEnum(String str) {
        this.str = str;
    }
}
