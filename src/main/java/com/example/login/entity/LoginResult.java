package com.example.login.entity;

/**
 * @author angus
 */

public class LoginResult extends Result<User> {
    boolean isLogin;

    public static LoginResult success(String msg, boolean isLogin) {
        return success(msg, isLogin, null);
    }

    public static LoginResult success(String msg, boolean isLogin, User data) {
        return new LoginResult("ok", msg, data, isLogin);
    }

    public static LoginResult failure(String msg) {
        return new LoginResult("fail", msg, null, false);
    }

    protected LoginResult(String status, String msg, User data, boolean isLogin) {
        super(status, msg, data);
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
