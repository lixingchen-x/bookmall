package com.lxc.result;

import com.lxc.constants.RegisterMsgEnum;
import com.lxc.constants.ResultEnum;
import org.springframework.stereotype.Component;

@Component
public class Results {

    public Result success() {

        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    public Result success(Object data) {

        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public Result fail() {

        Result result = new Result();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMsg(ResultEnum.FAIL.getMsg());
        return result;
    }

    /**
     * 下列三个methods为注册的状态返回
     */
    public Result registerSuccess() {

        Result result = new Result();
        result.setCode(RegisterMsgEnum.REGISTER_SUCCESS.getCode());
        result.setMsg(RegisterMsgEnum.REGISTER_SUCCESS.getMsg());
        return result;
    }

    public Result usernameExists() {

        Result result = new Result();
        result.setCode(RegisterMsgEnum.USERNAME_EXISTS.getCode());
        result.setMsg(RegisterMsgEnum.USERNAME_EXISTS.getMsg());
        return result;
    }

    public Result passwordShort() {

        Result result = new Result();
        result.setCode(RegisterMsgEnum.PASSWORD_SHORT.getCode());
        result.setMsg(RegisterMsgEnum.PASSWORD_SHORT.getMsg());
        return result;
    }
}
