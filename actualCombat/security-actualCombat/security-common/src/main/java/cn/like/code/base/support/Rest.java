package cn.like.code.base.support;

import cn.like.code.base.support.code.BasisErrorCode;
import cn.like.code.base.support.code.IErrorCode;
import cn.like.code.base.support.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * desc: Rest Api返回结果 <br>
 * details:
 * <pre>
 *     注意: 使用of()的时候 不设置code就是null
 * </pre>
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:28:47
 */
@Getter
@ToString
@Api(tags = "Rest Api返回结果")
public class Rest<T> implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 业务状态码 */
	@ApiModelProperty(name = "业务状态码")
	private Long code;
	/** 结果集 */
	@ApiModelProperty(name = "结果集")
	private T data;
	/** 描述 */
	@ApiModelProperty(name = "描述")
	private String msg;
	
	public Rest() {
		// to do nothing
	}
	
	public Rest(IErrorCode errorCode) {
		errorCode = Optional.ofNullable(errorCode).orElse(BasisErrorCode.FAILED);
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}
	
	// ========================== success
	
	public static <T> Rest<T> ok(T data) {
		BasisErrorCode aec = BasisErrorCode.SUCCESS;
		if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
			aec = BasisErrorCode.FAILED;
		}
		return restResult(data, aec);
	}
	
	// ========================== failed
	
	public static <T> Rest<T> failed(String msg) {
		return restResult(null, BasisErrorCode.FAILED.getCode(), msg);
	}
	
	public static <T> Rest<T> failed(IErrorCode errorCode) {
		return restResult(null, errorCode);
	}
	
	// ========================== restResult
	
	public static <T> Rest<T> restResult(T data, IErrorCode errorCode) {
		return restResult(data, errorCode.getCode(), errorCode.getMsg());
	}
	
	private static <T> Rest<T> restResult(T data, long code, String msg) {
		Rest<T> apiResult = new Rest<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		
		return apiResult;
	}
	
	// ========================== of
	
	public static <T> Rest<T> of(T data) {
		final Rest<T> rest = new Rest<>();
		rest.setData(data);
		
		return rest;
	}
	
	public Rest<T> msg(String msg) {
		this.msg = msg;
		return this;
	}
	
	public Rest<T> code(long code) {
		this.code = code;
		return this;
	}
	
	public Rest<T> errorCode(IErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
		
		return this;
	}
	
	/**
	 * desc: 判断当前请求是否是成功的 <br>
	 *
	 * @return: boolean
	 * @author: like 980650920@qq.com
	 * @date 2021-06-29 21:42:14
	 */
	public boolean ok() {
		return BasisErrorCode.SUCCESS.getCode() == code;
	}
	
	/**
	 * 服务间调用非业务正常，异常直接释放
	 */
	public T serviceData() {
		if (!ok()) {
			throw new ApiException(this.msg);
		}
		return data;
	}
	
	// ========================== set方法 私有
	
	private void setCode(long code) {
		this.code = code;
	}
	
	private void setData(T data) {
		this.data = data;
	}
	
	private void setMsg(String msg) {
		this.msg = msg;
	}
}

