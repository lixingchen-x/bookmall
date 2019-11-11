package com.lxc.constants;

public enum AddResults {

    SUCCESS(0, "success"), FAIL(1, "entity_exists");

    private AddResults(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
