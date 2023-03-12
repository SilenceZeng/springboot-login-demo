package com.example.login.entity;

/**
 * @author angus
 */

public class Result {
    String status;
    String msg;
    Boolean isLogin;
    Object data;

    public static Result failure(String msg) {
        return new Result("fail", msg, false);
    }

    public static Result success(String msg, Boolean isLogin) {
        return new Result("ok", msg, isLogin, null);
    }

    public static Result success(String msg, Boolean isLogin, Object data) {
        return new Result("ok", msg, isLogin, data);
    }

    public Result(String status, String msg, Boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public Result(String status, String msg, Boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean login) {
        isLogin = login;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
