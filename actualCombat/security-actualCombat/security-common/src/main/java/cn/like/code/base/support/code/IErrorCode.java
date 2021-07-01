package cn.like.code.base.support.code;

/**
 * desc: 错误码接口
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:56:30
 */
public interface IErrorCode {
	
	/**
	 * desc:
	 * 错误编码 -1、失败 0、成功
	 * <br>
	 *
	 * @return: long
	 * @author: like 980650920@qq.com
	 * @date 2021-06-29 20:56:18
	 */
	long getCode();
	
	/**
	 * desc: 错误描述
	 * <br>
	 *
	 * @return: {@link String }
	 * @author: like 980650920@qq.com
	 * @date 2021-06-29 20:56:10
	 */
	String getMsg();
}
