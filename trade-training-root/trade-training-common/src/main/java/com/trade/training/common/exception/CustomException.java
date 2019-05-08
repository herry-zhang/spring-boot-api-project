package com.trade.training.common.exception;

/**
 * 自定义异常
 *
 * @author herry-zhang
 * @date 2018-07-18 23:20:00
 */
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public CustomException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public CustomException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public CustomException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public CustomException(int code, String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
