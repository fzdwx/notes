package cn.like.code.base;

import cn.like.code.base.support.Rest;
import cn.like.code.base.support.code.ErrorCode;

/**
 * desc: base controller <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:49:48
 */
public abstract class BaseController {
	
	/**
	 * 请求成功
	 *
	 * @param data 数据内容
	 * @param <T>  对象泛型
	 * @return ignore
	 */
	protected <T> Rest<T> success(T data) {
		return Rest.ok(data);
	}
	
	/**
	 * 请求失败
	 *
	 * @param msg 提示内容
	 * @return ignore
	 */
	protected <T> Rest<T> failed(String msg) {
		return Rest.failed(msg);
	}
	
	/**
	 * 请求失败
	 *
	 * @param errorCode 请求错误码
	 * @return ignore
	 */
	protected <T> Rest<T> failed(ErrorCode errorCode) {
		return Rest.failed(errorCode);
	}
	
}
