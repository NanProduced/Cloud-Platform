package tech.nan.demo.domain.user;

import lombok.Getter;

@Getter
public enum UserType {

    /**
     * 全局管理员
     */
    SUPER_ROOT(0, "super_root"),

    /**
     * 组织管理员
     */
    ORG_ROOT(1, "org_root"),

    /**
     * 普通用户
     */
    NORMAL(2, "normal");

    private final Integer typeId;

    private final String typeName;

    UserType(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
