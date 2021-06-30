package cn.like.code.base.support.exception;

import cn.like.code.base.support.code.IErrorCode;

/**
 * desc: 异常 <br>
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:04:16
 * @see RuntimeException
 */
public abstract class BaseException extends RuntimeException {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5885155226898287919L;
	
	/**
	 * 错误码
	 */
	private IErrorCode errorCode;
	
	public BaseException(IErrorCode errorCode) {
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
	
	public final IErrorCode getErrorCode() {
		return errorCode;
	}
}
