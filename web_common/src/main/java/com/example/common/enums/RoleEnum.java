package com.example.common.enums;

public enum RoleEnum {
    SUPER_ADMIN(1L),
    ADMIN(2L),
    USER(3L);

    public final Long roleId;

    RoleEnum(Long roleId) {
        this.roleId = roleId;
    }
}
