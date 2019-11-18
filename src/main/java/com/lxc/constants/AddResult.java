package com.lxc.constants;

public enum AddResult {

    SUCCESS(0, "success"), FAIL(1, "entity_exists");

    private AddResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
