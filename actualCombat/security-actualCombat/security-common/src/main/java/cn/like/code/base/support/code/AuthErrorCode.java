package cn.like.code.base.support.code;

/**
 * desc: 身份验证错误代码 <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:56:46
 * @see Enum
 * @see ErrorCode
 */
public enum AuthErrorCode implements ErrorCode{
	
	
	;
	
	
	private final long code;
	private final String msg;
	
	AuthErrorCode(final long code, final String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	@Override
	public long getCode() {
		return code;
	}
	
	@Override
	public String getMsg() {
		return msg;
	}
	
	@Override
	public String toString() {
		return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
	}
}
