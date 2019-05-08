package com.trade.training.config;

/**
 * 错误码
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum ErrorCode {

    // 基础错误类型
    OK(200, "正确"),
    SC_INTERNAL_SERVER_ERROR(500, "未知异常，请联系管理员"),
    SC_NOT_FOUND(404, "路径不存在，请检查路径是否正确"),

    // 系统错误类型 10000
    SYSTEM_ARGUMENT_ERROR(10001, "参数错误"),
    SYSTEM_ARGUMENT_TYPE_MISMATCH(10002, "参数类型不匹配"),

    // 功能辅助错误类型 20000
    SUPPORT_VERIFICATION_CODE_INVALID(20001, "验证码不正确"),
    SUPPORT_SMS_VERIFY_SENT_FAILED(20002, "手机验证码发送失败"),
    SUPPORT_SMS_VERIFY_INVALID(20003, "手机验证码不正确"),
    SUPPORT_SMS_VERIFY_EXPIRE(20004, "手机验证码已过期"),

    // 系统用户信息错误相关类型 30000
    SYS_USER_AUTHENTICATION_FAILED(30001, "身份认证失败，请登录"),
    SYS_USER_NOT_FOUND(30002, "用户不存在"),
    SYS_USER_PASSWORD_ERROR(30003, "用户名或密码错误"),
    SYS_USER_LOCKED(30004, "账号已被锁定，请联系管理员"),
    SYS_USER_UNAUTHORIZED(30005, "未经授权，请联系管理员授权"),
    SYS_USER_NAME_EXISTED(30006, "用户名已存在"),
    SYS_USER_ADD_UNAUTHORIZED(30007, "不满足添加用户的权限要求"),
    SYS_USER_DELETE_ERROR(30008, "删除用户失败"),
    SYS_ROLE_NAME_EXISTED(30009, "角色名已存在"),
    SYS_ROLE_NOT_FOUND(30010, "角色不存在"),
    SYS_ROLE_USER_FOUND(30011, "角色有关联用户存在"),
    SYS_ROLE_NOT_DELETE(30012, "管理员角色不能删除"),
    SYS_ROLE_NOT_UPDATE(30013, "角色修改失败"),
    SYS_USER_NOTIFY_NOT_EXISTED(30014, "通知不存在");
    private String message;
    private int code;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
