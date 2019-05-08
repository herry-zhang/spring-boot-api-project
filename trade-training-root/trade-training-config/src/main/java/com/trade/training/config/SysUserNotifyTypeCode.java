package com.trade.training.config;

/**
 * 系统用户消息类型
 *
 * @author herry-zhang
 * @date 2018-7-19 10:22:51
 */
public enum SysUserNotifyTypeCode {
    USER_FINISH_TASK(1, "有用户完成了任务"),
    USER_VIP_EXPIRE(2, "有用户会员到期"),
    USER_ADD_WORK_ORDER(3, "有用户添加了工单"),
    SYS_USER_APPLY_DISMISSAL_WORK_ORDER(4, "运营人员申请驳回工单"),
    SYS_USER_DIRECTOR_CHANGE_WORK_ORDER(5, "主管处理了工单"),
    SYS_USER_LONG_TERM_UNTREATED(6, "您有一个工单超过两天未处理");

    private String message;
    private int code;

    SysUserNotifyTypeCode(int code, String message) {
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
