package cn.like.code.base.support.exception;

import cn.like.code.base.support.code.ErrorCode;

/**
 * desc: 异常 <br>
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:04:16
 * @see RuntimeException
 */
public class BaseException extends RuntimeException {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5885155226898287919L;
	
	/**
	 * 错误码
	 */
	private ErrorCode errorCode;
	
	public BaseException(ErrorCode errorCode) {
		super(errorCode.getMsg());
		this.errorCode = errorCode;
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
