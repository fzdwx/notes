package cn.like.code.base.support.exception;

import cn.like.code.base.support.code.IErrorCode;

/**
 * desc: Rest api 异常 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:06:54
 * @see BaseException
 */
public class ApiException extends BaseException {
	
	public ApiException(IErrorCode errorCode) {
		super(errorCode);
	}
	
	public ApiException(String message) {
		super(message);
	}
	
	public ApiException(Throwable cause) {
		super(cause);
	}
	
	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
