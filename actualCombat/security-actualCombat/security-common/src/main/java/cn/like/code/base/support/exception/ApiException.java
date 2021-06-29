package cn.like.code.base.support.exception;

import cn.like.code.base.support.code.ErrorCode;

/**
 * desc: Rest api 异常 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:06:54
 * @see BaseException
 */
public class ApiException extends BaseException {
	
	public ApiException(ErrorCode errorCode) {
		super(errorCode);
	}
	
	public ApiException(String message) {
		super(message);
	}
}
