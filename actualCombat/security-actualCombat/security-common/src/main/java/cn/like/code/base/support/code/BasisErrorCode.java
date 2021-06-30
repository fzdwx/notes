package cn.like.code.base.support.code;

/**
 * desc: 基础 错误代码 <br>
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:57:09
 * @see Enum
 * @see IErrorCode
 */
public enum BasisErrorCode implements IErrorCode {
	/**
	 * 失败
	 */
	FAILED(-1, "操作失败"),
	/**
	 * 成功
	 */
	SUCCESS(0, "执行成功"),
	
	;
	
	private final long code;
	private final String msg;
	
	BasisErrorCode(final long code, final String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static BasisErrorCode fromCode(long code) {
		BasisErrorCode[] ecs = BasisErrorCode.values();
		for (BasisErrorCode ec : ecs) {
			if (ec.getCode() == code) {
				return ec;
			}
		}
		return SUCCESS;
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
