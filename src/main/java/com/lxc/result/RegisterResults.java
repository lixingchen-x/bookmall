package com.lxc.result;

import com.lxc.constants.RegisterMsg;
import org.springframework.stereotype.Component;

/**
 * 下列为注册的状态返回
 */
@Component
public class RegisterResults {

    public Result registerSuccess() {

        Result result = new Result();
        result.setCode(RegisterMsg.REGISTER_SUCCESS.getCode());
        return result;
    }

    public Result usernameExists() {

        Result result = new Result();
        result.setCode(RegisterMsg.USERNAME_EXISTS.getCode());
        return result;
    }

    public Result passwordShort() {

        Result result = new Result();
        result.setCode(RegisterMsg.PASSWORD_SHORT.getCode());
        return result;
    }
}
