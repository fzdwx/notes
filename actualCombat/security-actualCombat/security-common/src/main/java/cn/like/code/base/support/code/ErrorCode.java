/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.like.code.base.support.code;

/**
 * desc: 错误码接口
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 20:56:30
 */
public interface ErrorCode {
	
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
