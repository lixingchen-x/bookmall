package com.lxc.constants;

public class Results {

    public static Result success() {

        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    public static Result success(Object data) {

        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static Result fail() {

        Result result = new Result();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMsg(ResultEnum.FAIL.getMsg());
        return result;
    }

    // 下列为注册的状态返回
    public static Result registerSuccess() {

        Result result = new Result();
        result.setCode(RegisterMsgEnum.REGISTER_SUCCESS.getCode());
        result.setMsg(RegisterMsgEnum.REGISTER_SUCCESS.getMsg());
        return result;
    }

    public static Result usernameExists() {

        Result result = new Result();
        result.setCode(RegisterMsgEnum.USERNAME_EXISTS.getCode());
        result.setMsg(RegisterMsgEnum.USERNAME_EXISTS.getMsg());
        return result;
    }

    public static Result passwordShort() {

        Result result = new Result();
        result.setCode(RegisterMsgEnum.PASSWORD_SHORT.getCode());
        result.setMsg(RegisterMsgEnum.PASSWORD_SHORT.getMsg());
        return result;
    }
}
